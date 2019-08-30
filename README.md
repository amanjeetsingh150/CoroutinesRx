# CoroutinesRx
A display for battle of Asynchronousity between Coroutines and Rx. This repository consist of how the reactive world of 
<b>RxJava</b> looks like in <b>Coroutines</b> world.

Battles:

<UL>
<LI>
<H2>Exception Handling</H2>
Shows standard way of handling errors in RxJava and coroutines here in <a href = "https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/exception/ExceptionViewModel.kt">ExceptionViewModel</a>.
The standard ways of handling errors in Rx and coroutines are as follows here:
<ul>
<li><b>RxJava</b>: RxJava handles exception with variety of operators <a href="https://github.com/ReactiveX/RxJava/wiki/Error-Handling-Operators">here</a>.</li>
<li><b>Coroutines</b>: Coroutines exception handling depends on type of coroutine builder. Flows have its operator to catch errors on the chain.</li>
</ul>
</LI>
<LI>
<H2>Zipping Results</H2>
Shows how 2 API calls will be zipped in RxJava and coroutines. Rx has its own zip operator and coroutines needs implementing the 
own zip operator. We have to customize the operator to make it scalable to zip various calls:
<ul>
<li><b>RxJava</b>: RxJava has its own zip operator and we can zip api calls with it using 2 observable and the BiFunction as shown 
<a href="https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/zip/ZipViewModel.kt#L86">here.</a></li>
<li><b>Coroutines</b>: Coroutines implementation of zip has been done here in <a href= "https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/utils/Extensions.kt#L27">Extensions</a> file. </li>
<li><b>Flows</b>: Flows can also be used to zip api calls. As shown <a href = "https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/zip/ZipViewModel.kt#L66">here</a> it has variety of operators available.</li>
</ul>
</LI>
<LI>
<H2>State Management</H2>
<ul>
<li><b>RxJava</b>: Using RxJava we can acheive Reactive and functional paradigm which can easily offer you architecure to stream
out UI events and different stream based events which helps you to reduce a state for your program. It is rich with operators, lifecycle management is done very easily and thread switching is also easy.
You can have a look at example <a href = "https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/state/StateViewModel.kt">here</a>.</li>
<li><b>Coroutines</b>: Coroutines were having a gap of missing a reactive and composable part which is now fulfilled by the Kotlin Flows
API. It is representation of cold streams and can be simply thought upon as suspension based reactive streams. Example with 
flows is given <a href = "https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/state/CoroutinesStateViewModel.kt">here</a>.</li>
</ul>
</LI>
<LI>
<H2>Performace comparison</H2>
The performance comparison is done by performing same kind of operations with coroutines and Rx both. By using 2 different number of data set first with 10,000 iterations and then with 100,000 iterations. The operation is analogy of n number of data set on which you do some operations like flatmapping the result to get shows result and further adding to list. 
<ul>
<li>The change in memory is shown belown in both case. The operation on the data with Rx can be seen <a href = "https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/performance/PerformanceViewModel.kt#L32">here</a> and coroutines is <a href = "https://github.com/amanjeetsingh150/CoroutinesRx/blob/master/app/src/main/java/com/developers/coroutinesrx/performance/PerformanceViewModel.kt#L55">here</a>:<br><br>  
<b>10,000 iterations</b>  
<table>
<tr>
<th></th>
<th>Base Memory</th>
<th>Max change</th> 
<th>Delta change</th>  
</tr>
<tr>
  <td>Rx</td>
  <td>72.6 MB</td>
  <td>93.2 MB</td>
  <td>20.6 MB</td>
</tr>
<tr>
  <td>Coroutines</td>
  <td>64.6 MB</td>
  <td>83.4 MB</td>
  <td>18.8 MB</td>
</tr>  
</table> 
RxJava:<br> 
<img src ="https://user-images.githubusercontent.com/12881364/64049743-7aa86c80-cb93-11e9-8724-ad94b06d9f5c.png"/> <br>
Coroutines:<br>
<img src = "https://user-images.githubusercontent.com/12881364/64049869-e68ad500-cb93-11e9-8cf9-1c3320d08be7.png"/>  
</li>
<br><br>  
<b>100,000 iterations</b> 
<table>
<tr>
<th></th>
<th>Base Memory</th>
<th>Max change</th> 
<th>Delta change</th>  
</tr>
<tr>
  <td>Rx</td>
  <td>65.5 MB</td>
  <td>97.7 MB</td>
  <td>32.2 MB</td>
</tr>
<tr>
  <td>Coroutines</td>
  <td>65 MB</td>
  <td>89 MB</td>
  <td>24 MB</td>
</tr>  
</table>   
RxJava:<br>
<img src ="https://user-images.githubusercontent.com/12881364/64050073-982a0600-cb94-11e9-9d8e-c1a52e112b11.png"/> <br>
Coroutines:<br> 
<img src = "https://user-images.githubusercontent.com/12881364/64050118-af68f380-cb94-11e9-9cd9-a6a22fb62433.png"/>   
</ul>  
</LI>
</UL>
