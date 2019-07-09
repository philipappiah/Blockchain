package javaapplication1;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import static java.security.spec.MGF1ParameterSpec.SHA256;
import java.sql.Time;
import java.util.regex.*;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static javax.xml.crypto.dsig.DigestMethod.SHA256;

public class Solution{
  
    
public static final class Block{
    int index;
    String timestamp;
    String data;
    String prevHash;
    String hash;
    public Block(int index, String timestamp,String data, String prevHash) throws NoSuchAlgorithmException{
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.prevHash = prevHash;
        this.hash = this.sha256();
          
    }
    public String sha256() throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] message = md.digest((this.index+this.prevHash+this.timestamp+this.data).getBytes());
        BigInteger num = new BigInteger(1,message);
        String hash = num.toString(16);
        while(hash.length() < 32){
            hash = "0" + hash;
        }
        return hash;
    }
    
}

public static final class BlockChain{
    ArrayList<Block>chain = new ArrayList<>();
    
    public BlockChain() throws NoSuchAlgorithmException{
        this.chain.add(this.createGenesisBlock());
        
    }
    
    
    public Block createGenesisBlock() throws NoSuchAlgorithmException{
        return new Block(0,"01/07/2019","Genesis Block","0");
    }
    
    public Block getLatestBlock(){
        return this.chain.get(this.chain.size()-1);
    }
    
    public void addBlock(Block newblock) throws NoSuchAlgorithmException{
        newblock.prevHash = this.getLatestBlock().hash;
        newblock.hash = newblock.sha256();
        this.chain.add(newblock);
        
        
    }
    
    public boolean isvalidchain() throws NoSuchAlgorithmException{
        for(int i = 1; i < this.chain.size(); i++){
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i-1);
            if(!currentBlock.hash.equals(currentBlock.sha256())){
                return false;
            }
            
            if(!currentBlock.prevHash.equals(previousBlock.hash)){
                return false;
            }
        }
        return true;
    }
    
    
    public void display() throws NoSuchAlgorithmException{
        for(Block s :chain){
            System.out.println(s.data+" "+s.timestamp+" "+s.hash+" "+s.prevHash+" ");
        }
    }
}
     
    
        
     
        
  public static void main(String[]args) throws NoSuchAlgorithmException{
      String s1 = "2000 Ghana Cedis";
      String s2 = "100,000 Ghana Cedis";
       Date date = new Date();
      Block b1  = new Block(1, date.toString(),s1,"0");
      Block b2 = new Block(2, date.toString(),s2,"0");
      
      BlockChain d = new BlockChain();
      d.addBlock(b1);
      d.addBlock(b2);
      System.out.println(d.isvalidchain());
      d.display();
 
      
     }
   
}



