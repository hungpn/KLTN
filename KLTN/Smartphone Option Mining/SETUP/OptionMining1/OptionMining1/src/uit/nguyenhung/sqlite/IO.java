package uit.nguyenhung.sqlite;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IO 
{
	public String readFile( String path)
	{
		String result = "";
		try 
		{
			//tạo luồng nối đến tập tin
			FileInputStream is = new FileInputStream(path);
			//Dùng phương tiện Scanner để đọc
			Scanner input = new Scanner(is,"UTF-8");//đọc theo bảng mã utf-8
			//Đọc tệp tin
			int i = 1;
			//trong khi còn dòng để đọc
			while(input.hasNextLine()) 
			{
				//Đọc 1 dòng
				String line = input.nextLine();
				if( line != "")
				{
					result += line + "\n";
				}
			}
				//đóng luồng
				is.close();
			input.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		return result;
	}
	
	public List<String> readFileToListString( String path)
	{
		List<String> listResult = new ArrayList<String>();
		try 
		{
			//tạo luồng nối đến tập tin
			FileInputStream is = new FileInputStream(path);
			//Dùng phương tiện Scanner để đọc
			Scanner input = new Scanner(is,"UTF-8");//đọc theo bảng mã utf-8
			//Đọc tệp tin
			int i = 1;
			//trong khi còn dòng để đọc
			while(input.hasNextLine()) 
			{
				//Đọc 1 dòng
				String line = input.nextLine();
				if( line != "")
				{
					listResult.add(line);
				}
			}
				//đóng luồng
				is.close();
			input.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		return listResult;
	}
}

