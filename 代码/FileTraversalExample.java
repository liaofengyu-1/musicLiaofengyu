package com.lfy.excel.test;

import com.alibaba.fastjson.JSON;
import com.lfy.excel.vo.Song;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FileTraversalExample {
    public static void main(String[] args) {
        // 指定文件夹路径
        String folderPath = "E:\\Github歌曲仓库";

        // 指定保存文件名的文本文件路径和名称
        String outputFile = "E:\\json格式.txt";

        // 创建一个 File 对象来表示文件夹
        File folder = new File(folderPath);

        // 创建 FileWriter 对象以便向输出文件写入内容
        try (FileWriter writer = new FileWriter(outputFile)) {
            // 调用递归方法来遍历文件夹下的所有文件并写入到文本文件中
            traverseFolder(folder, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("文件名称已保存到：" + outputFile);
    }

    public static void traverseFolder(File folder, FileWriter writer) throws IOException {
        ArrayList<String> songs = new ArrayList<>();
        // 判断文件夹是否存在
        if (folder.exists() && folder.isDirectory()) {
            // 获取文件夹下的所有文件
            File[] files = folder.listFiles();

            // 遍历文件列表
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        // 将数据放入一个list
                        songs.add(file.getName());
                    } else if (file.isDirectory()) {
                        // 递归遍历子文件夹
                        traverseFolder(file, writer);
                    }
                }
            }
        }
        String songJson = setFileName(songs);
        if ("[]".equals(songJson)) {
            return;
        }
        System.out.println(songJson);
        writer.write(songJson);
    }
    public static String setFileName(List<String> songNames) {
        List<Song> songList = new ArrayList<>();
        int id = 0;
        for (String songName : songNames) {
            if (!songName.endsWith(".mp3")) {
                continue;
            }
            // 先去掉后面的.mp3
            songName = songName.substring(0, songName.length() - 4);


            // 创建一个song对象
            Song song = new Song();
            song.setName(songName);
            song.setId(id++);
            song.setUrl(getUrl(songName));
            // 判断是日语还是中文
            if (containsJapanese(songName)) { //为true表示含有日文
                song.setLanguages("日语");
            } else {
                song.setLanguages("粤语");
            }
            songList.add(song);
        }
        // 序列化成json格式
        return JSON.toJSONString(songList);
    }




    public static boolean containsJapanese(String input) {
        // 使用正则表达式判断字符串中是否包含日文字符
        return input.matches(".*[\\p{Script=Hiragana}\\p{Script=Katakana}].*");
    }


    // 拼接url
    public static String getUrl(String songName) {
        String result = "";
        String encoding = "UTF-8";
        try {
            String encode = URLEncoder.encode(songName, encoding);
            String replace = encode.replace("+", "%20");
            result = "https://github.com/liaofengyu-1/musicFromLiaofengyu/blob/master/" + replace  + ".mp3";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}