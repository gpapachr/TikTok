import org.apache.tika.*;
import org.xml.sax.SAXException;

import java.io.*;

public class Mp4Parse{

   int i = 01;// Files count starts from 1
   InputStream inputStream = new FileInputStream(file);
   OutputStream outputStream;
   int chunkSize = 100*1024*1024;
   int parts = inputStream.available() / chunkSize;
   String fileCount;
   int read;
   int streamSize = 0;
   byte[] chunks;

    public byte[] generateChunks(String path) {
        try {
            File file = new File(path);//File read from Source folder to Split.
            if (file.exists()) {

                String videoFileName = file.getName().substring(0, file.getName().lastIndexOf(".")); // Name of the videoFile without extension
                File splitFile = new File("Videos_Split/"+ videoFileName);//Destination folder to save.
                if (!splitFile.exists()) {
                    splitFile.mkdirs();
                    System.out.println("Directory Created -> "+ splitFile.getAbsolutePath());
                }

                
                while ((read = inputStream.read()) != -1) {

                    if (chunkSize == streamSize) {
                        if (i != parts) {
                            fileCount = String.format("%02d", i); 
                            videoFile = splitFile.getAbsolutePath() +"/"+ fileCount +"_"+ file.getName();
                            //chunks.add(videoFile)
                            outputStream = new FileOutputStream(videoFile);
                            System.out.println("File Created Location: "+ videoFile);
                            i++;
                        }
                    }                    
                    
                    outputStream.write(read);
                    streamSize++;
                }

                inputStream.close();
                outputStream.close();
                System.out.println("Total files Split ->"+ parts);
            } 
            else {
                System.err.println(file.getAbsolutePath() +" File Not Found.");
            }
            return chunks;
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
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