package com.vortex.common.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title:FileUtils.java</p>
 * <p>Description:文件管理类</p>
 * @author Johnny.xu
 * @date 2016年11月9日
 */
public class FileUtils {
	public static String AppCode = "vortex";
	
	private final static String Log = "log";
	private final static String File = "file";
	private final static String Img = "img";
	private final static String Video = "video";
	
	private static String mEnvironmentDirectory
		= Environment.getExternalStorageDirectory().getPath()+ "/" + AppCode;

	public static void initPath() {
		mEnvironmentDirectory = Environment.getExternalStorageDirectory().getPath()+ "/" + AppCode;
	}

	/**
	 * 获取图片Base64字节
	 * @param imagePath
	 * @return
	 */
	public static String getImageBase64Str(String imagePath) {
		String str = "";
		try {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			str = Base64.encodeToString(Bitmap2Bytes(bitmap), Base64.DEFAULT);
			if (!bitmap.isRecycled())
				bitmap.recycle();
		} catch (Exception e) {}
		return str;
	}

	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		return baos.toByteArray();
	}

	/**
	 * 获取当前时间日期
	 *
	 * @param format
	 *            自定义格式,例：yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static String getFormatTime(String format) {
		Date date = new Date();
		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat(format);
		String time = df.format(date);
		return time;
	}
	
	/**
	 * 获取日志文件地址
	 */
	public static String getLogDir() {
		return getDir(Log);
	}

	/**
	 * 获取文件地址
	 */
	public static String getFileDir() {
		return getDir(File);
	}
	
	/**
	 * 获取视频文件地址
	 */
	public static String getVideoDir() {
		return getDir(Video);
	}
	
	/**
	 * 获取图片文件地址
	 */
	public static String getImgDir() {
		return getDir(Img);
	}
	
	public static String getDir(String...dir) {
		StringBuilder sb = new StringBuilder(mEnvironmentDirectory);
		for (String str : dir) {
			sb.append("/" + str);
		}
		mkDir(sb.toString());
		return sb.toString();
	}
	
	/**
	 * 创建文件目录
	 * @param dir 如：/sdcard/log
	 */
	public static java.io.File mkDir(String dir) {
		if (isSDCARDMounted()) {
			java.io.File file = null;
			try {
				file = new File(dir);
				if (!file.exists()) {
					file.mkdirs();
				}
				return file;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 判断SDCARD是否有效
	 */
	public static boolean isSDCARDMounted() {
		String status = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(status))
			return true;
		return false;
	}
	
	public static boolean saveImage(Bitmap bitmap) {
		boolean isOk = false;
		String fname = FileUtils.getImgDir() + "/" + UUIDGenerator.getUUID() + ".png";
		if (bitmap != null) {
			System.out.println("bitmap got!");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(fname);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				isOk = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {}
				}
			}
		} else {
			System.out.println("bitmap is NULL!");
		}
		return isOk;
	}
	
	public static boolean saveImage(String fileName, Bitmap bitmap) {
		boolean isOk = false;
		String fname = FileUtils.getImgDir() + "/" + fileName;
		if (bitmap != null) {
			System.out.println("bitmap got!");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(fname);
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				isOk = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {}
				}
			}
		} else {
			System.out.println("bitmap is NULL!");
		}
		return isOk;
	}

	public static String saveImage(Context context, String filepath) {
		Bitmap bitmap = scaleImage(filepath, 640, 480);
		// 为了防止重复 这里用uuid作为名字
		String uuid = UUIDGenerator.getUUID();
		String picPath = FileUtils.getImgDir() + "/" + uuid + ".jpg";
		java.io.File file = new File(picPath);
		try {
			FileOutputStream outStream = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outStream)) {
				outStream.flush();
				outStream.close();
			}
			ContentResolver cr = context.getContentResolver();
			MediaStore.Images.Media.insertImage(cr, bitmap, uuid, "");
			updateGallery(context, "file://" + Environment.getExternalStorageDirectory());
			bitmap.recycle();
			return picPath;
		} catch (FileNotFoundException e) {} catch (IOException e) {}
		return "";
	}
	
	public static boolean saveImage(byte[] data) {
		boolean isOk = false;
		String fileName = FileUtils.getImgDir() + "/" + UUIDGenerator.getUUID() + ".png";
		if (data != null) {
			System.out.println("bitmap got!");
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(fileName);
				fos.write(data);
				isOk = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.flush();
						fos.close();
					} catch (IOException e) {}
				}
			}
		} else {
			System.out.println("bitmap is NULL!");
		}
		return isOk;
	}

	public static long getFileTime(String path) {

		java.io.File file = new File(path);

		if (file.exists()) {
			return new Date(file.lastModified()).getTime();
		} else {
			return 0;
		}
	}

	public static Bitmap scaleImage(String imagePath, int requestWidth, int requestHeight) {
		Bitmap newBitmap = null;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(imagePath, options);

			options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);

			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

			String orientation = getExifOrientation(imagePath, "0");

			Matrix matrix = new Matrix();
			matrix.postRotate(Float.valueOf(orientation));

			newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
		} catch (Exception e) {
			VorLog.v("ImageUtils", "scaleImage error");
		}

		return newBitmap;
	}

	public static String getExifOrientation(String path, String orientation) {
		Method exif_getAttribute;
		Constructor<ExifInterface> exif_construct;
		String exifOrientation = "";

		int sdk_int = 0;
		try {
			sdk_int = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (Exception e1) {
			sdk_int = 3; // assume they are on cupcake
		}
		if (sdk_int >= 5) {
			try {
				exif_construct = android.media.ExifInterface.class
						.getConstructor(new Class[] { String.class });
				Object exif = exif_construct.newInstance(path);
				exif_getAttribute = android.media.ExifInterface.class
						.getMethod("getAttribute", new Class[] { String.class });
				try {
					exifOrientation = (String) exif_getAttribute.invoke(exif,
							android.media.ExifInterface.TAG_ORIENTATION);
					if (exifOrientation != null) {
						if (exifOrientation.equals("1")) {
							orientation = "0";
						} else if (exifOrientation.equals("3")) {
							orientation = "180";
						} else if (exifOrientation.equals("6")) {
							orientation = "90";
						} else if (exifOrientation.equals("8")) {
							orientation = "270";
						}
					} else {
						orientation = "0";
					}
				} catch (InvocationTargetException ite) {
					orientation = "0";
				} catch (IllegalAccessException ie) {
					System.err.println("unexpected " + ie);
					orientation = "0";
				}
			} catch (NoSuchMethodException nsme) {
				orientation = "0";
			} catch (IllegalArgumentException e) {
				orientation = "0";
			} catch (InstantiationException e) {
				orientation = "0";
			} catch (IllegalAccessException e) {
				orientation = "0";
			} catch (InvocationTargetException e) {
				orientation = "0";
			}
		}
		return orientation;
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqW, int reqH) {
		final int h = options.outHeight;
		final int w = options.outWidth;
		int inSampleSize = 1;

		if (h > reqH || w > reqW) {
			final int heightRatio = Math.round((float) h / (float) reqH);
			final int widthRatio = Math.round((float) w / (float) reqW);

			inSampleSize = Math.min(heightRatio, widthRatio);
		}
		return inSampleSize;
	}

	private static void updateGallery(Context context, String filename) {
		MediaScannerConnection.scanFile(context, new String[] { filename }, null,
				new MediaScannerConnection.OnScanCompletedListener() {
					public void onScanCompleted(String path, Uri uri) {

					}
				});
	}

	// 在手机上打开文件的method
	public static void openFile(Context context, java.io.File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		/* 调用getMIMEType()来取得MimeType */
		/* 设置intent的file与MimeType */
		intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}


}
