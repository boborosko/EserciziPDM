package pdm.esercitazione;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RssReader extends ListActivity {
 
 private List<String> item = new ArrayList<String>();
 
   /** Called when the activity is first created. */
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);

       try {
   URL rssUrl = new URL("http://www.gazzetta.it/rss/Ultimora.xml");//prendo i dati dal broswer
   SAXParserFactory mySAXParserFactory = SAXParserFactory.newInstance(); //inizio il parsing dei dati 
   SAXParser mySAXParser = mySAXParserFactory.newSAXParser();
   XMLReader myXMLReader = mySAXParser.getXMLReader();
   RSSHandler myRSSHandler = new RSSHandler();         
   myXMLReader.setContentHandler(myRSSHandler);
   InputSource myInputSource = new InputSource(rssUrl.openStream());
   myXMLReader.parse(myInputSource); //a questo punto prendo i dati nella Url 
       } catch (MalformedURLException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  } catch (ParserConfigurationException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  } catch (SAXException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  } catch (IOException e) {
    	   // TODO Auto-generated catch block
    	   e.printStackTrace();
    	  }
       
       ArrayAdapter<String> itemList = new ArrayAdapter<String>(this, R.layout.rss_list, item);//definisco l'adapter che inserirà i dati nella ListView
       setListAdapter(itemList);                                                               
        }
   protected void onlistItemClick(ListView l,View v,int position,long id) {//il metodo onClickListener gestisce il click sui link
		Uri uri = Uri.parse(item.get(position));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);//tramite l'intent apro il testo su un altra pagina
		startActivity(intent);
	}
      
   private class RSSHandler extends DefaultHandler{
    final int sUnknown = 0;
    final int sTitle = 1;
    int state = sUnknown;
    
  @Override
  public void startDocument() throws SAXException {
   // TODO Auto-generated method stub
  }

  @Override
  public void endDocument() throws SAXException {
   // TODO Auto-generated method stub
  }

  @Override
  public void startElement(String uri, String localName, String qName,
    Attributes attributes) throws SAXException {
   // TODO Auto-generated method stub
   if (localName.equalsIgnoreCase("title"))
   {
    state = sTitle;
   }
   else
   {
    state = sUnknown;
   }
  }

  @Override
  public void endElement(String uri, String localName, String qName)
    throws SAXException {
   // TODO Auto-generated method stub
   state = sUnknown;
  }

  @Override
  public void characters(char[] ch, int start, int length)
    throws SAXException {
   // TODO Auto-generated method stub
   String strCharacters = new String(ch, start, length);
   if (state == sTitle)
   {
    item.add(strCharacters);
    
   }
  }
    
   }
}
   