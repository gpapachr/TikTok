import java.io.*;
import java.util.*;

public class Mp4Parse{
   
   private File file;
   
   private InputStream inputStream;
   private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
   private int chunkSize = 512*1024;
   private int parts;
   private int read;
   private int streamSize = 0;
   private ArrayList<byte[]> chunks = new ArrayList<byte[]>();

   Mp4Parse(String path){
      file = new File(path);
      try{
         inputStream = new FileInputStream(file);
         parts = (inputStream.available() / chunkSize) + 1;  
      }
      catch(Exception e){
         e.printStackTrace();
      }
   }


   public void parse(){
      int i = 1;
      try {       
         read = inputStream.read();
         while ((read) != -1) {
            outputStream.write(read);
            streamSize++;
            if (chunkSize == streamSize && i!=parts) {                     
               chunks.add(outputStream.toByteArray());
               i++;
               streamSize = 0;
               outputStream.reset();
            }  
            read = inputStream.read();
            if (i==parts && read==-1){
               chunks.add(outputStream.toByteArray());
            }
         }

         System.out.println("Chunks splitted: " + chunks.size());

         inputStream.close();
         outputStream.close();
      } 
      catch (Exception e) {
         e.printStackTrace();
      }      
   }

   public byte[] getChunk(int i){
      return chunks.get(i);
   }


   public int getChunksNumber() {
      return chunks.size();
   }


   public void delete() {
      try {
         inputStream.reset();
      } catch (IOException e) {
        
      }
      outputStream.reset();
      parts = 0;
      read = 0;
      streamSize = 0;
      chunks.clear();
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
