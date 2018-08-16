package com.commit451.uresourcespoet

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.SAXException
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.StringWriter
import java.io.Writer
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerConfigurationException
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * Helps generate XML configuration files for Android
 */
class ResourcesPoet private constructor() {

  companion object {
    private const val ELEMENT_RESOURCES = "resources"

    private var documentBuilderFactory: DocumentBuilderFactory? = null
    private var documentBuilder: DocumentBuilder? = null
    private var transformerFactory: TransformerFactory? = null

    /**
     * Create a new builder
     *
     * @return poet
     */
    @JvmStatic
    fun create(): ResourcesPoet {
      init()
      val document = documentBuilder!!.newDocument()
      val resources = document.createElement(ELEMENT_RESOURCES)
      document.appendChild(resources)
      return create(document, resources)
    }

    /**
     * Creates a builder on top of the current resources XML file
     *
     * @param file the resources file you want to add to
     * @return poet
     */
    @JvmStatic
    fun create(file: File): ResourcesPoet {
      try {
        return create(FileInputStream(file))
      } catch (e: FileNotFoundException) {
        throw IllegalStateException("Unable to parse the resource file you passed. Make sure it is properly formatted", e)
      }
    }

    /**
     * Creates a builder on top of the current resources XML file
     *
     * @param inputStream the input stream of the resources file you want to add to
     * @return poet
     */
    @JvmStatic
    fun create(inputStream: InputStream): ResourcesPoet {
      init()
      try {
        val document = documentBuilder!!.parse(inputStream)
        val resources: Element
        val list = document.getElementsByTagName(ELEMENT_RESOURCES)
        if (list == null || list.length == 0) {
          resources = document.createElement(ELEMENT_RESOURCES)
          document.appendChild(resources)
        } else {
          resources = list.item(0) as Element
        }
        return create(document, resources)
      } catch (e: IOException) {
        throw IllegalStateException("Unable to parse the resource file you passed. Make sure it is properly formatted", e)
      } catch (e: SAXException) {
        throw IllegalStateException("Unable to parse the resource file you passed. Make sure it is properly formatted", e)
      }
    }

    private fun create(document: Document, resourceElement: Element): ResourcesPoet {
      val poet = ResourcesPoet()
      poet.document = document
      poet.resourceElement = resourceElement
      return poet
    }

    private fun init() {
      if (documentBuilderFactory == null || documentBuilder == null) {
        try {
          documentBuilderFactory = DocumentBuilderFactory.newInstance()
          documentBuilder = documentBuilderFactory!!.newDocumentBuilder()
        } catch (exception: ParserConfigurationException) {
          //Welp
          throw IllegalStateException("Unable to create a ResourcePoet")
        }

        transformerFactory = TransformerFactory.newInstance()
      }
    }
  }

  private lateinit var document: Document
  private lateinit var resourceElement: Element
  private var indent: Boolean = false

  /**
   * Add an attr to the config
   *
   * @param attr the defined attribute
   * @return poet
   */
  fun addAttr(attr: Attr): ResourcesPoet {
    //<attr name="gravityX" format="float"/>
    val element = document.createElement(Type.ATTR.toString())
    element.setAttribute("name", attr.name)
    if (!attr.formats.isEmpty()) {
      var formatString = ""
      for (format in attr.formats) {
        formatString = formatString + format.toString() + "|"
      }
      //remove last |
      formatString = formatString.substring(0, formatString.length - 1)
      element.setAttribute("format", formatString)
    }
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Add a boolean to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addBool(name: String, value: Boolean): ResourcesPoet {
    addBool(name, value.toString())
    return this
  }

  /**
   * Add a boolean to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addBool(name: String, value: String): ResourcesPoet {
    //<bool name="is_production">false</bool>
    val element = document.createElement(Type.BOOL.toString())
    element.setAttribute("name", name)
    element.appendChild(document.createTextNode(value))
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Add a color to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addColor(name: String, value: String): ResourcesPoet {
    //<color name="color_primary">#7770CB</color>
    val element = document.createElement(Type.COLOR.toString())
    element.setAttribute("name", name)
    element.appendChild(document.createTextNode(value))
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Add a comment to the config
   *
   * @param comment the comment to add
   * @return poet
   */
  fun addComment(comment: String): ResourcesPoet {
    val commentNode = document.createComment(comment)
    resourceElement.appendChild(commentNode)
    return this
  }

  /**
   * Add a drawable to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addDrawable(name: String, value: String): ResourcesPoet {
    //<drawable name="logo">@drawable/logo</drawable>
    val bool = document.createElement(Type.DRAWABLE.toString())
    bool.setAttribute("name", name)
    bool.appendChild(document.createTextNode(value))
    resourceElement.appendChild(bool)
    return this
  }

  /**
   * Add a dimension to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addDimension(name: String, value: String): ResourcesPoet {
    //<drawable name="logo">@drawable/logo</drawable>
    val bool = document.createElement(Type.DIMENSION.toString())
    bool.setAttribute("name", name)
    bool.appendChild(document.createTextNode(value))
    resourceElement.appendChild(bool)
    return this
  }

  /**
   * Add an id to the config
   *
   * @param id the id
   * @return poet
   */
  fun addId(id: String): ResourcesPoet {
    //        <item
    //                type="id"
    //        name="id_name" />
    val bool = document.createElement(Type.ID.toString())
    bool.setAttribute("name", id)
    bool.setAttribute("type", "id")
    resourceElement.appendChild(bool)
    return this
  }

  /**
   * Add an integer to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addInteger(name: String, value: Int?): ResourcesPoet {
    addInteger(name, value.toString())
    return this
  }

  /**
   * Add an integer to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addInteger(name: String, value: String): ResourcesPoet {
    //<drawable name="logo">@drawable/logo</drawable>
    val bool = document.createElement(Type.INTEGER.toString())
    bool.setAttribute("name", name)
    bool.appendChild(document.createTextNode(value.toString()))
    resourceElement.appendChild(bool)
    return this
  }

  /**
   * Add an integer array to the config
   *
   * @param name the name
   * @param values the value
   * @return poet
   */
  fun addIntegerArray(name: String, values: List<Int>): ResourcesPoet {
    val integers = ArrayList<String>()
    for (value in values) {
      integers.add(value.toString())
    }
    addIntegerArrayStrings(name, integers)
    return this
  }

  /**
   * Add an integer array to the config
   *
   * @param name the name
   * @param values the value
   * @return poet
   */
  fun addIntegerArrayStrings(name: String, values: List<String>): ResourcesPoet {
    // <integer-array name="numbers">
    //      <item>0</item>
    //      <item>1</item>
    // </integer-array>
    val element = document.createElement(Type.INTEGER_ARRAY.toString())
    element.setAttribute("name", name)
    for (value in values) {
      //Does this mess up the ordering?
      val valueElement = document.createElement("item")
      valueElement.appendChild(document.createTextNode(value))
      element.appendChild(valueElement)
    }
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Add a plural strings array to the config
   *
   * @param name the name
   * @param plurals the plurals
   * @return poet
   */
  fun addPlurals(name: String, plurals: List<Plural>): ResourcesPoet {
    //    <plurals name="numberOfSongsAvailable">
    //        <item quantity="one">Znaleziono %d piosenkę.</item>
    //        <item quantity="few">Znaleziono %d piosenki.</item>
    //        <item quantity="other">Znaleziono %d piosenek.</item>
    //    </plurals>
    val element = document.createElement(Type.PLURALS.toString())
    element.setAttribute("name", name)
    for (plural in plurals) {
      //Does this mess up the ordering?
      val valueElement = document.createElement("item")
      valueElement.setAttribute("quantity", plural.quantity.toString())
      valueElement.appendChild(document.createTextNode(plural.value))
      element.appendChild(valueElement)
    }
    resourceElement.appendChild(element)

    return this
  }

  /**
   * Add a string to the config
   *
   * @param name the name
   * @param value the value
   * @return poet
   */
  fun addString(name: String, value: String): ResourcesPoet {
    //<string name="app_name">Cool</string>
    val element = document.createElement(Type.STRING.toString())
    element.setAttribute("name", name)
    element.appendChild(document.createTextNode(value))
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Add a String array to the config
   *
   * @param name the name
   * @param values the value
   * @return poet
   */
  fun addStringArray(name: String, values: List<String>): ResourcesPoet {
    //<string-array name="country_names">
    //      <item>Country</item>
    //      <item>United States</item>
    // </string-array>
    val element = document.createElement(Type.STRING_ARRAY.toString())
    element.setAttribute("name", name)
    for (value in values) {
      //Does this mess up the ordering?
      val valueElement = document.createElement("item")
      valueElement.appendChild(document.createTextNode(value))
      element.appendChild(valueElement)
    }
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Add a style to the config
   *
   * @param name the name
   * @param parentRef a ref to the style parent
   * @return poet
   */
  @JvmOverloads
  fun addStyle(name: String, parentRef: String? = null, styleItems: List<StyleItem>? = null): ResourcesPoet {
    //<style name="AppTheme.Dark" parent="Base.AppTheme.Dark"/>
    val element = document.createElement(Type.STYLE.toString())
    element.setAttribute("name", name)
    if (parentRef != null) {
      element.setAttribute("parent", parentRef)
    }
    if (styleItems != null) {
      for (item in styleItems) {
        val valueElement = document.createElement("item")
        valueElement.setAttribute("name", item.name)
        valueElement.appendChild(document.createTextNode(item.value))
        element.appendChild(valueElement)
      }
    }
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Add a typed array to the config
   *
   * @param name the name
   * @param values the value
   * @return poet
   */
  fun addTypedArray(name: String, values: List<String>): ResourcesPoet {
    //<array name="country_names">
    //      <item>Country</item>
    //      <item>United States</item>
    // </array>
    val element = document.createElement(Type.TYPED_ARRAY.toString())
    element.setAttribute("name", name)
    for (value in values) {
      val valueElement = document.createElement("item")
      valueElement.appendChild(document.createTextNode(value))
      element.appendChild(valueElement)
    }
    resourceElement.appendChild(element)
    return this
  }

  /**
   * Remove the resource which matches the name and type
   * @param type the type of the resource you wish to remove
   * @param name the name of the element to remove
   * @return poet
   */
  fun remove(type: Type, name: String): ResourcesPoet {
    val nodeList = resourceElement.getElementsByTagName(type.toString())
    for (i in 0..nodeList.length - 1) {
      val node = nodeList.item(i)
      if (node is Element && name == node.getAttribute("name")) {
        //For some reason, this will remove the element and leave a line break in its place
        //Somewhat unfortunate but I do not think there is much we could do about it
        resourceElement.removeChild(nodeList.item(i))
      }
    }
    return this
  }

  /**
   * Get the value of the current resource of this type and name
   * @param type the type
   * @param name the name
   * @return the value or null if it does not exist
   */
  fun value(type: Type, name: String): String? {
    val nodeList = resourceElement.getElementsByTagName(type.toString())
    for (i in 0..nodeList.length - 1) {
      val node = nodeList.item(i)
      if (node is Element && name == node.getAttribute("name")) {
        //For some reason, this will remove the element and leave a line break in its place
        //Somewhat unfortunate but I do not think there is much we could do about it
        return nodeList.item(i).textContent
      }
    }
    return null
  }

  /**
   * Specify if you want the output to be indented or not
   *
   * @param indent true if you want indentation. false if not. Default is false
   * @return poet
   */
  fun indent(indent: Boolean): ResourcesPoet {
    this.indent = indent
    return this
  }

  /**
   * Build the XML to a string
   *
   * @return the xml as a string
   */
  fun build(): String {
    val writer = StringWriter()
    val result = StreamResult(writer)
    build(result)
    return writer.toString()
  }

  /**
   * Build the XML to a file. You should call [File.createNewFile] or validate that the file exists
   * before calling
   *
   * @param file the file to output the XML to
   */
  fun build(file: File) {
    val result = StreamResult(file)
    build(result)
  }

  /**
   * Build the XML to the [OutputStream]
   *
   * @param outputStream the output stream to output the XML to
   */
  fun build(outputStream: OutputStream) {
    val result = StreamResult(outputStream)
    build(result)
  }

  /**
   * Build the XML to the [Writer]
   *
   * @param writer the writer to output the XML to
   */
  fun build(writer: Writer) {
    val result = StreamResult(writer)
    build(result)
  }

  /**
   * Build the XML to the [StreamResult]
   *
   * @param result the result
   */
  private fun build(result: StreamResult) {
    try {
      val transformer = transformerFactory!!.newTransformer()
      transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8")
      if (indent) {
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
      }
      val source = DOMSource(document)
      transformer.transform(source, result)
    } catch (e: TransformerConfigurationException) {
      throw RuntimeException("Something is seriously wrong with the ResourcePoet configuration")
    } catch (e: TransformerException) {
      throw RuntimeException("Transformer exception when trying to build result")
    }
  }
}
