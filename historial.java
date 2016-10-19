package nano.serviciosweb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;


public class historial extends Activity {

  String clave = "123456";
  String usuario = "admin";

  String res;
  String aux;
  String telefono = "6468371927";

  Button buscar;
  TextView datos;
  EditText pass;


  private ListView lstClientes;

  String NAMESPACE = "http://tempuri.org/";
  String URL = "http://stg3.controlbox.net/losdoradoscargo/WS_ASMX(SOAP)_CODIGO/service.asmx";
  String METHOD_NAME = "CONSULTA_HISTORIALES";
  String SOAP_ACTION = "http://tempuri.org/CONSULTA_HISTORIALES";

  static final String KEY_CODIGO = "codigo";
  static final String KEY_MENSAJE = "mensaje";
  static final String KEY_CODIGO_INTERNO = "codigointerno";
  static final String KEY_MENSAJE_INTERNO = "mensajeinterno";
  /**
   * ATTENTION: This was auto-generated to implement the App Indexing API.
   * See https://g.co/AppIndexing/AndroidStudio for more information.
   */
  private GoogleApiClient mClient;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_historial);

    datos = (TextView) findViewById(R.id.datos);
    buscar = (Button) findViewById(R.id.buscar);
    pass = (EditText)findViewById(R.id.pass);

    //telefono = getIntent().getStringExtra("telefono");
    datos.setText(telefono);

    lstClientes = (ListView) findViewById(R.id.lstClientes);

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
  }

  //
//
  public void EnviaronClick(View v) {

    Thread nt = new Thread() {

      @Override
      public void run() {

        //clave = pass.getText().toString();
        SoapObject resquest = new SoapObject(NAMESPACE, METHOD_NAME);
        resquest.addProperty("usuario", usuario.toString());
        resquest.addProperty("clave", clave.toString());
        resquest.addProperty("nro_telefono", telefono.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.implicitTypes = true;

        envelope.setOutputSoapObject(resquest);

        HttpTransportSE transporte = new HttpTransportSE(URL, 60000);
        transporte.debug = true;

        //res = "Punto 1";

        //int PRETTY_PRINT_INDENT_FACTOR = 4;

        try {
          transporte.call(SOAP_ACTION, envelope);
          SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
          //SoapObject resultado_xml = (SoapObject) envelope.getResponse();

          //String resultadoXml = resultado_xml.toString();

          //StringBuilder result = new StringBuilder();
          //XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
          //XmlPullParser xpp = factory.newPullParser();
          //FileInputStream fis = ctx.openFileInput(result);
          //BufferedReader reader = new BufferedReader(new StringReader(resultadoXml));

          //String line;
          //while ((line = reader.readLine()) != null){
            //result.append(line); //pasa toda la entrada al StringBuilder
          //}

          //JSONObject xmlJSONObj = new JSONObject(resultadoXml);
          //String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
           //res = Html.fromHtml(resultado_xml.toString()).toString();

          res = resultado_xml.toString();

          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
          DocumentBuilder builder;

          builder = factory.newDocumentBuilder();
          Document doc = builder.parse(new InputSource( new StringReader(res)));

          for (int i = 0; i < doc.getFirstChild().getChildNodes().getLength(); i++) {

            NodeList nodeList = doc.getFirstChild().getChildNodes();
            Element element = (Element) nodeList.item(i);
            aux += element.getNodeName() + ": ";
            aux += element.getFirstChild().getNodeValue() + ".\n";
          }

          res = aux;

          //El siguiente proceso lo hago porque el JSONObject necesita un string y tengo
          //que transformar al BufferReader a String
          //esto lo hago atraves de un StringBuilder

           //Le introdusco en un BufferReader

//          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//          DocumentBuilder builder = factory.newDocumentBuilder();
//          Document d1 = builder.parse(new InputSource(new StringReader(result)));
//
//          JSONObject respuestaJSON = new JSONObject(d1.toString()); //Creo un JSONObject apartir de un JSONObject
//
//          String resultJSON = respuestaJSON.getString("Codigo");//Estado es el nombre del campo en el JSON
//
//          if (resultJSON.equals("1")){ //hay alumnos a mostrar
//
//            //startActivity(siguiente);
//            res = "Activo";
//
//          }
//          else if (resultJSON.equals("2")){
//            res = "No existe el usuario";
//          }


          //res = d1.getFirstChild().toString();
          //res = resultado_xml.toString();

        } catch (XmlPullParserException e) {
          res = e.toString();
        } catch (SocketTimeoutException e) {
          res = e.toString();
        } catch (IOException e) {
          res = e.toString();
        }catch (ParserConfigurationException e) {
          e.printStackTrace();
        } catch (SAXException e) {
          e.printStackTrace();
        }

      }

      //return res;

    };

    //clase para sincronizacion
    runOnUiThread(new Runnable() {
      @Override
      public void run() {

        Toast.makeText(historial.this, res, Toast.LENGTH_LONG).show();
        datos.setText(res);

      }

    });

    nt.start();

    //nt.start();
    datos.setText(res);
    //Toast.makeText(historial.this, res, Toast.LENGTH_LONG).show();

  }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
  public Action getIndexApiAction() {
    Thing object = new Thing.Builder()
      .setName("historial Page") // TODO: Define a title for the content shown.
      // TODO: Make sure this auto-generated URL is correct.
      .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
      .build();
    return new Action.Builder(Action.TYPE_VIEW)
      .setObject(object)
      .setActionStatus(Action.STATUS_TYPE_COMPLETED)
      .build();
  }

  @Override
  public void onStart() {
    super.onStart();

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    mClient.connect();
    AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
  }

  @Override
  public void onStop() {
    super.onStop();

    // ATTENTION: This was auto-generated to implement the App Indexing API.
    // See https://g.co/AppIndexing/AndroidStudio for more information.
    AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
    mClient.disconnect();
  }
}
