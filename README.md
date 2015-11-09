# PictureDownloaderGallery
this is a viewGroup which can download pictures from web and resize the picture for comfortable automatically.

# Preview

![效果图](http://img.blog.csdn.net/20151109094011100)


#Get Started

 	repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
 	dependencies {
       compile 'com.github.liangpengfei:PictureDownloaderGallery:1af6f11154'
    }
    
    
#Usage
XML 

	<xhome.uestcfei.com.picturedownloadergallery.PictureDownloaderGallery
        android:layout_width="wrap_content"
        android:id="@+id/downloader"
        android:layout_height="wrap_content"/>
        
 JAVA
 
 downloaderGallery.setUrls(urls);

        downloaderGallery.setImageItemListener(new PictureDownloaderGallery.ImageItemListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "the " + position + "item is clicked!", Toast.LENGTH_LONG).show();
            }
        });
        
        
This lib is under improvement ,any good idea or any bug is welcome to start a issues


#License

No license,You can share it as you like it! Wish you can learn some basic tips from my repository!