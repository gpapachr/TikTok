import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.tika.*;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp4.MP4Parser;
import org.apache.tika.sax.BodyContentHandler;

import org.xml.sax.SAXException;

import java.io.*;


public class Mp4Parse {
    
            File file = new File("C:/Documents/Despicable Me 2 - Trailer (HD) - YouTube.mp4");//File read from Source folder to Split.
            if (file.exists()) {

            String videoFileName = file.getName().substring(0, file.getName().lastIndexOf(".")); // Name of the videoFile without extension
            File splitFile = new File("C:/Documents/Videos_Split/"+ videoFileName);//Destination folder to save.
            if (!splitFile.exists()) {
                splitFile.mkdirs();
                System.out.println("Directory Created -> "+ splitFile.getAbsolutePath());
            }

            int i = 01;// Files count starts from 1
            InputStream inputStream = new FileInputStream(file);
            String videoFile = splitFile.getAbsolutePath() +"/"+ String.format("%02d", i) +"_"+ file.getName();// Location to save the files which are Split from the original file.
            OutputStream outputStream = new FileOutputStream(videoFile);
            System.out.println("File Created Location: "+ videoFile);
            int chunkSize = 512 * 1024;
            int totalPartsToSplit = inputStream.available() / chunkSize;
            int streamSize = 0;
            int read = 0;
            while ((read = inputStream.read()) != -1) {

                if (splitSize == streamSize) {
                    if (i != totalPartsToSplit) {
                        i++;
                        String fileCount = String.format("%02d", i); // output will be 1 is 01, 2 is 02
                        videoFile = splitFile.getAbsolutePath() +"/"+ fileCount +"_"+ file.getName();
                        outputStream = new FileOutputStream(videoFile);
                        System.out.println("File Created Location: "+ videoFile);
                        streamSize = 0;
                    }
                }
                outputStream.write(read);
                streamSize++;
            }

            inputStream.close();
            outputStream.close();
            System.out.println("Total files Split ->"+ totalPartsToSplit);
        } else {
            System.err.println(file.getAbsolutePath() +" File Not Found.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}




























/*private class ChunkHandler {
   private int size = 0;
   private int fileNumber = -1;
   private OutputStreamWriter out = null;

   final byte maxChunkSize = 512 * 1024;
   final File outputDir = new File("/tmp/");

   public void bytes(byte[] b, int start, int length) throws IOException {
      if (out == null || size+length > maxChunkSize) {
         if (out != null) out.close();
         fileNumber++;
         File f = new File(outputDir, "output-" + fileNumber + ".mp4");
         out = new OutputStreamWriter(new FileOutputStream(f, "UTF-8"));
      }
      out.write(b, start, length);
   }
   public void close() throws IOException {
      if (out != null) out.close();
   }
}

public void parse(File file) {
   InputStream stream = new FileInputStream(file);
   Parser p = new AutoDetectParser();
   Metadata meta =new Metadata();
   ContentHandler handler = new ChunkHandler();
   ParseContext parse = new ParseContext();

   p.parse(stream,handler,meta, context);
   ((ChunkHandler)handler).close();
}


public class Mp4Parse {

   public static void main(final String[] args) throws IOException,SAXException, TikaException {

      //detecting the file type
      BodyContentHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
      FileInputStream inputstream = new FileInputStream(new File("example.mp4"));
      ParseContext pcontext = new ParseContext();
      
      //Html parser
      MP4Parser MP4Parser = new MP4Parser();
      MP4Parser.parse(inputstream, handler, metadata,pcontext);
      System.out.println("Contents of the document:  :" + handler.toString());
      System.out.println("Metadata of the document:");
      String[] metadataNames = metadata.names();
      
      for(String name : metadataNames) {
         System.out.println(name + ": " + metadata.get(name));
      }
   }  
}*/