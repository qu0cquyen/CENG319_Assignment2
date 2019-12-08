# ExoPlayer
<h3>Introduction</h3> 
<hr>
<p>&emsp;&emsp;ExoPlayer is an application level media player for Android, which is widely used as an alternative tool to Android’s Media Player. Besides, supporting the lower level API (Minimum API level is 16) and being able to stream audio and video locally and over the Internet. It also has a significant advantage over the convention Android’s Media Player such as playing a variety of media formats includes DASH and StreamSmoothing adaptive formats. Unlike Android’s Media Player, ExoPlayer is easy to customize and extend for advanced usage purposes. ExoPlayer is being utilized by Google Play Movies and Youtube.  </p>

<h3>History</h3>
<hr>
<p>&emsp;&emsp;The component is first released on March 25th, 2015 with the version of r1.2.3 to developer. </p>
<p>&emsp;&emsp;Its next major release (r2.0.0) is on September 14th, 2019 with a number of significant adjustments such as API, architecture and providing new features. Some of the its highlight features in this version are Playlist support, DASH multi-period support and live playbacks for DASH and SmoothStreaming finding. </p>
<p>&emsp;&emsp;On 3rd November 2017, the prefix ‘r’ was removed from release version – 2.6.0 – instead of r2.6.0. A minor number of new features is introduced, this version concentrated more on improvements and bug fixing. </p>
<p>&emsp;&emsp;In the release of 2.10.0 on April 15th 2019, more features are added into this version, for example, decoder re-use between playbacks improvement and the default minimum buffer and maximum buffer size are the same for video playbacks. </p>
<p>&emsp;&emsp;Finally, the latest version is 2.10.8 which released on November 19th 2019 with some minor bugs get fixed. </p>
<p>&emsp;&emsp;The minimum requirements to apply ExoPlayer into Android System are Android 4.1 and API level 16 (Jelly Bean). However, some features may require higher API level and Android version. Click on this <a href='https://github.com/google/ExoPlayer/blob/release-v2/RELEASENOTES.md'>link</a> for more detail information about the release.</p>

<h3>A brief and simple Tutorial</h3> 
<hr> 
<p>&emsp;&emsp;This tutorial will walk you through all the major methods to implement the player into your Android app. </p>

<h4>Step 1: First thing first, we need to implement the dependencies of ExoPlayer to our app: </h4>
<p align='center'><img src='https://i.ibb.co/g7Ftjs6/Step1.png' align='middle'/></p>
<p>&emsp;&emsp;These dependencies will install the ExoPlayer UI, Core Library and Dash technique into our app. </p>
<h4>Step 2: Next, we might want to Turn on Java 8 Compiler support: </h4>
<p align='center'><img src='https://i.ibb.co/yQXSsFq/Step2.png' align='middle'/></p>
<p>&emsp;&emsp;The compileOptions should be placed inside android{….} </p>
<h4>Step 3:Now, it is time to put ExoPlayer in our layout file:  </h4>
<p align='center'><img src='https://i.ibb.co/0KbMwG2/Step3.png' align='middle'/></p>
<p>&emsp;&emsp;Your app’s layout should look like this:  </p>
<p align='center'><img src='https://i.ibb.co/LYCKRVn/Step3-2.png' align='middle'/></p>
<p>&emsp;&emsp;If you wish to customize your player, you can refer to this:  </p>
<p align='center'><img src='https://i.ibb.co/Rc0BkQP/Step3-3.png' align='middle'width='50%' height='50%'/></p>
<p>&emsp;&emsp;Click on this <a href='https://medium.com/fungjai/playing-video-by-exoplayer-b97903be0b33'>link</a> for more information.</p>
<h4>Step 4:  Go to our activity and start coding. We need to find the id of our ExoPlayer first in onCreate function which is automatically created.  </h4>
<p align='center'><img src='https://i.ibb.co/z8QfBJ1/Step4.png' align='middle' width='50%' height='50%'/></p>
<h4>Step 5: We initialize for our ExoPlayer:   </h4>
<p align='center'><img src='https://i.ibb.co/cbVyHYh/Step5.png' align='middle'/></p>
<p>&emsp;&emsp;Our app will be using Smooth Streaming technique for video streaming. Smooth Streaming is known as a hybrid media delivery method and is based on HTTP progressive download. Simplicity speaking, this technique can real time detect user’s internet bandwidth and CPU capabilities. For instance, when we are watching a video on Youtube, if our Internet connection is suddenly dropped or slower than usual, the video quality will drop along with the internet bandwidth get narrowed. For more information about what Smooth Streaming is, you can refer to these websites for more insight information:   </p>
<ul>
    <li>https://www.encoding.com/microsoft-smooth-streaming/</li>
    <li>https://www.iis.net/downloads/microsoft/smooth-streaming</li>
    <li>https://www.globaldots.com/blog/streaming-microsoft-smoothstreaming</li>
</ul>
<p>&emsp;&emsp;In our codes, we use BandwidthMeter to provide the current available bandwidth estimation in which it is used during data transfer listening. DefaultTrackSelector is responsible for selecting the available tracks in the media source, with the combination of AdaptiveTrackSelection.Factory, this will choose the highest quality track based on the current network condition and buffer state. Then we use our trackSelection to create a new instance of simple Exoplayer. Finally, we bind our defined player to our ExoPlayer. </p>
<h4>Step 6: Up until now, we have defined our player mechanism on streaming technique. Next, we need to build a Media Source in order to make our player to be able to stream video with the given link.  </h4>
<p align='center'><img src='https://i.ibb.co/r5pvL02/Step6.png' align='middle' width='50%' height='50%'/></p>
<p>&emsp;&emsp;DefaultHTTPDataSourceFactory is used to fetch the data over HTTP/HTTPS with a User-Agent. To know more about what User-Agent is, please refer to <a href='https://www.whatismybrowser.com/detect/what-is-my-user-agent'>this</a>. In order to stream our video, we need to use an extractor to extract the media data (a decoder to read media files) from a container <a href='https://exoplayer.dev/supported-formats.html'>format</a>. Then, we use the extractor to get the media data from our provided link with the support of DefaultHTTPDataSourceFactory and create a Media Source. </p>
<h4>Step 7: Provides link and create a Media Source under the code of binding the player. </h4>
<p align='center'><img src='https://i.ibb.co/gzdMfvh/Step7.png' align='middle' width='50%' height='50%'/></p>
<h4>Step 8: Optimize resources </h4>
<p>&emsp;&emsp;Our app may use up numerous resources such as memory, CPU and network connections. While they may be used for a short period of time. This is why it is crucial to free up any redundant resources. In order to optimize the resources, we need to rewrite the application lifecycle.</p>
<ul>
  <li><p>Firstly, we override the onStart() and onResume() method: </p>
    <p align='center'><img src='https://i.ibb.co/0hJHP7b/Step8.png' align='middle' width='50%' height='50%'/></p>
    <p>&emsp;&emsp;Since multiple windows feature is supported from later Android API Level 24, we can see our app, however, it may not run in split window mode. Therefore, we may put the initialize method inside onStart() in order to run the player even if we are in split window mode. Whereas, android does not support to the Multiple Windows features before API Level 24 so we can wait as long as possible until we are able to grab the media resources. </p>
    <p>&emsp;&emsp;In addition, we want to let our users to have full screen video streaming experience, to do that we need to implement a method called hideSystemUi() and place It inside onResume() method. </p>
    <p align='center'><img src='https://i.ibb.co/WgmdZ8V/Step8-2.png' align='middle' width='50%' height='50%'/></p>
  </li>
  <li><p>Next, we put releasePlayer() method into onPause() and onStop() function.</p>
    <p align='center'><img src='https://i.ibb.co/cLp22ZY/Step8-3.png' align='middle' width='50%' height='50%'/></p>
    <p>&emsp;&emsp;onStop() method is not always called before API level 24; as a result, we should release the player as soon as possible in onPause() method. While in higher API level, onStop() always get called, our activity is visible in paused state so we wait to release the player until onStop. </p>
    <p>&emsp;&emsp;releasePlayer() method is responsible for storing playback position, current window value, play or pause state, freeing player’s resources and setting up player to null eventually. </p>
    <p align='center'><img src='https://i.ibb.co/n8m1h4d/Step8-4.png' align='middle' width='50%' height='50%'/></p>
  </li>
</ul>
<h4>Step 9: In this final step, we will provide media state to the player. </h4>
<p align='center'><img src='https://i.ibb.co/m9P00Jk/Step9.png' align='middle'/></p>
<p>&emsp;&emsp;The codes should be written before the end of Initialize method. The details of each function do can be found at the Final preparation of this <a href='https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2'>link</a>. </p>

<h3>References</h3>
<hr>
<p>Altomare, F. (n.d.). Streaming: Microsoft SmoothStreaming. Retrieved from https://www.globaldots.com/blog/streaming-microsoft-smoothstreaming.</p>
<p>Chayabanjonglerd, M. (2018, April 29). Playing video by ExoPlayer. Retrieved from https://medium.com/fungjai/playing-video-by-exoplayer-b97903be0b33.</p>
<p>ExoPlayer. (n.d.). ExoPlayer. Retrieved from https://exoplayer.dev/.</p>
<p>ExoPlayer. (n.d.). ExoPlayer. Retrieved from https://exoplayer.dev/supported-formats.html.</p>
<p>Google. (n.d.). google/ExoPlayer. Retrieved from https://github.com/google/ExoPlayer/blob/release-v2/RELEASENOTES.md.</p>
<p>Media streaming with ExoPlayer. (n.d.). Retrieved from https://codelabs.developers.google.com/codelabs/exoplayer-intro/#0.</p>
<p>Media streaming with ExoPlayer. (n.d.). Retrieved from https://codelabs.developers.google.com/codelabs/exoplayer-intro/#2.</p>
<p>Microsoft. (n.d.). Smooth Streaming. Retrieved from https://www.iis.net/downloads/microsoft/smooth-streaming.</p>
<p>Microsoft Smooth Streaming. (n.d.). Retrieved from https://www.encoding.com/microsoft-smooth-streaming/.</p>
<p>What is my User Agent? (n.d.). Retrieved from https://www.whatismybrowser.com/detect/what-is-my-user-agent.</p>
