import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class calculation {
    public static int[] symbol(int number) {			//生成运算符，其中0-3代表加减乘除
        int[] arr = calculation.intNub(3, number);
        return arr;
    }

    public static int[] intNub(int max, int number) {    //生成一组随机数
        int[] arr = new int[number];
        for (int i = 0; i < number; ++i) {
            arr[i] = (int)(Math.random()*(max + 1));
        }
        return arr;
    }

    public static void cal(int[] a, int[] b, int[] c, int number, String path1, String path2) throws IOException {   //输出算式及结果
        for(int i=0;i<number;i++) {
        	switch(c[i]) {
        	case 0:
        		outPut(Integer.toString(a[i])+"+"+Integer.toString(b[i]),path1);
        		outPut(Integer.toString(a[i]+b[i]),path2);
        		break;
        	case 1:
        		if(a[i]>=b[i]) {		//确保不会产生负数
        			outPut(Integer.toString(a[i])+"-"+Integer.toString(b[i]),path1);
            		outPut(Integer.toString(a[i]-b[i]),path2);
        		}
        		else {
        			outPut(Integer.toString(b[i])+"-"+Integer.toString(a[i]),path1);
            		outPut(Integer.toString(b[i]-a[i]),path2);
        		}
        		break;
        	case 2:
        		if(a[i]==0) {	//确保乘法不出现0
        			outPut(Integer.toString(a[i]+1)+"*"+Integer.toString(b[i]),path1);
            		outPut(Integer.toString((a[i]+1)*b[i]),path2);
        		}
        		else if(b[i]==0) {
        			outPut(Integer.toString(a[i])+"*"+Integer.toString(b[i]),path1);
            		outPut(Integer.toString(a[i]*(b[i]+1)),path2);
        		}
        		else {
        			outPut(Integer.toString(a[i])+"*"+Integer.toString(b[i]),path1);
            		outPut(Integer.toString(a[i]*b[i]),path2);
        		}
        		break;
        	case 3:
        		if(b[i]==0) {	//确保分母不为0
        			outPut(Integer.toString(a[i])+"/"+Integer.toString(b[i]),path1);
            		outPut(Integer.toString(a[i]/(b[i]+1)),path2);
        		}
        		else {
        			outPut(Integer.toString(a[i])+"/"+Integer.toString(b[i]),path1);
            		outPut(Integer.toString(a[i]/b[i]),path2);
        		}
        		break;
        	}
        }
        
    }

    public static void outPut(String str, String path) throws IOException {		//输出结果到文本
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        String con = str;
        fileOutputStream.write(con.getBytes());
        fileOutputStream.write("\r\n".getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    
    public static String readFile(String path) {      //读取文本内容
        BufferedReader br = null;
		StringBuffer sb = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path))); //这里可以控制编码
			sb = new StringBuffer();
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		String data = new String(sb);
    	return data;
    }
    
    public static int[] change(String[] str) {		//将字符+-*/转为0-3的数字
    	int[] c = new int[str.length];
    	for(int i=0;i<str.length;i++) {
    		if(str[i]=="+") c[i]=0;
    		else if(str[i]=="-") c[i]=1;
    		else if(str[i]=="*") c[i]=2;
    		else c[i]=3;
    	}
    	return c;
    }
    
    public static void main(String[] args) throws IOException {
        int max = Integer.parseInt(args[0]);
        int number = Integer.parseInt(args[1]);
        cal(intNub(max,number),intNub(max,number),symbol(number), number, args[2], args[3]);
        
        String data = readFile(args[4]);		//逐个字符读取文本内容
        List<String> list = new ArrayList<String>();
        for(int i=0;i<data.length();i++){
            String str = String.valueOf(data.charAt(i));
                      list.add(str);           
        }
        String[] arr = list.toArray(new String[list.size()]);
        int[] a = new int[arr.length];
        int[] b = new int[arr.length];
        String[] c = new String[arr.length];
        for(int i=0,j=0;i<arr.length;i=i+4,j++) {
        	a[j] = Integer.valueOf(arr[i]).intValue();
        }
        for(int i=2,j=0;i<arr.length;i=i+4,j++) {
        	b[j] = Integer.valueOf(arr[i]).intValue();
        }
        for(int i=1,j=0;i<arr.length;i=i+4,j++) {
        	c[j] = arr[i];
        }
        
        
        cal(a,b,change(c),arr.length,args[5],args[6]);
    }
}
