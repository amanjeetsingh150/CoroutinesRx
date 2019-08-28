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
</UL>
