package qilin.caiqiaolinpan;

import android.os.Environment;

public class Tools {
	// check if the img is stored on SD card
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
}
