# VkNewsClient

#2.11 VkNewsClient. Создаем шапку поста

Задаём цвета для темы


private val DarkColorScheme = darkColorScheme(
    primary = Black900,
    secondary = Black900,
    tertiary = Pink80,
    onPrimary = Color.White,
    onSecondary = Black500,
    surfaceVariant = Black900
)

private val LightColorScheme = lightColorScheme(
    primary = Color.White,
    secondary = Color.White,
    tertiary = Pink40,
    onPrimary = Black900,
    onSecondary = Black500,
    surfaceVariant = Color.White

)

Реализуем верхнее меню для поста VK в отдльном файле





@Composable

fun PostCard(){
    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary)
        ) {
		//Строка
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
            ){
			//Первая картинка
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(id = R.drawable.post_comunity_thumbnail),
                contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
			//Два текста вертикально 
            Column(modifier = Modifier
                .weight(1f), <------------- параметр к у LinerLayout
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Text(text = "dev//null",
                    color = MaterialTheme.colorScheme.onPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "14:00",
                    color = MaterialTheme.colorScheme.onSecondary)
            }
            Spacer(modifier = Modifier.width(8.dp))
			// Вместо Image используем Icon чтобы задать цвет tint
            Icon(imageVector = Icons.Rounded.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary)
        }
    }
}

@Preview
@Composable
fun PostCardLight(){
    VkNewsClientTheme(darkTheme = false) {
        PostCard()
    }
}

@Preview
@Composable
fun PostCardDark(){
    VkNewsClientTheme(darkTheme = true) {
        PostCard()
    }
}

#2.12 VkNewsClient. Дорабатываем карточку поста

@Composable
fun PostCard(){
    Card(modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary)
        ){

        Column (modifier = Modifier.padding(8.dp)){
            PostHeader()
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.temp_text))
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.post_content_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth)
            Spacer(modifier = Modifier.height(8.dp))
            Statistics()
        }

    }

}

@Composable
private fun Statistics(){
    Row {
        Row(modifier = Modifier.weight(1f)) {
            IconWithText(iconResId = R.drawable.ic_views_count, text = "35")
        }
        Row(modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            IconWithText(iconResId = R.drawable.ic_share, text = "35")
            IconWithText(iconResId = R.drawable.ic_comment, text = "35")
            IconWithText(iconResId = R.drawable.ic_like, text = "35")
        }

    }
}

@Composable
private fun IconWithText(iconResId: Int, text : String){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = iconResId),
            contentDescription = null ,
            tint = MaterialTheme.colorScheme.onSecondary)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text,
            color = MaterialTheme.colorScheme.onSecondary)
    }
}
@Composable
private fun PostHeader(){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = R.drawable.post_comunity_thumbnail),
            contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier
            .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(text = "dev//null",
                color = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "14:00",
                color = MaterialTheme.colorScheme.onSecondary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary)
    }
}

##Material Components

#3.1 Введение в Material Components

Slot API - гибкая настройка Composable-функций
1) У Button нет атрибута text,но можно добавить любое кол-во элементов Text
2) modifier.weight()
 Если сделать так
  setContent {
            Text(text = "Text", modifier = Modifier.weight(1f))
			}
то weight будет не доступна

А так доступна, т.к. отностися к ColumnScope

setContent {
          Column { <--- this:ColumnScope
                Text(text = "Text", modifier = Modifier.weight(1f))
            }
	}
Поэтому если мы хотим использовать weigth в такой функции

    @Composable
    fun TestText(text : String, count : Int)
    {
        repeat(count){
            Text(text = text)
        }
    }
	
То нужно писать её как расширение над ColumnScope и вызывать внутри Column

Column {
            TestText(text = "text", count = 3)
        }
Приложение Compose Material Catalog

#3.2 Scaffold и BottomNavigation

Scaffold - экрна

@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {}, <--- Верхнее меню
    bottomBar: @Composable () -> Unit = {}, <---- Нижнее меню
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit
) ...

//Боковая панель в material3
@Composable
fun Test(){
    ModalNavigationDrawer(drawerContent = {
        val items = listOf(Icons.Default.Close, Icons.Default.Clear, Icons.Default.Call)

        ModalDrawerSheet {
            Spacer(Modifier.height(12.dp))
            items.forEach { item ->
                NavigationDrawerItem(
                    selected = false,
                    icon = { Icon(item, contentDescription = null) },
                    label = { Text(text = item.name)},
                    onClick = {}
                )
            }
        }
    }) {
        TestScaffold()
    }

}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TestScaffold()
    {
        Scaffold (
            topBar = {
                TopAppBar(title = { Text(text = "Top App Bar") },
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    })
            },
            bottomBar = {


                NavigationBar {
                    repeat(4) {
                        NavigationBarItem(selected = false, onClick = { /*TODO*/ }, icon = {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                        })
                    }
                }
            }


        ){
            Text(text = "Scafold content",
                modifier = Modifier.padding(it))
        }
    }

}

#3.3 State и рекомпозиция

Создадим главное окно для VKNews c нижним меню навигации

Элементы навигации
sealed class NavigationItem(
    val titleResId : Int,
    val icon: ImageVector
){
    object Home : NavigationItem (R.string.navigation_item_main, Icons.Outlined.Home)
    object Favorite : NavigationItem (R.string.navigation_item_favourite, Icons.Outlined.Favorite)
    object Profile : NavigationItem (R.string.navigation_item_profile, Icons.Outlined.Person)
}

@Composable
fun MainScreen(){
    Scaffold (
        bottomBar = {
            NavigationBar {
                var selectedItemPosition = 0 <---- отмеченный элемент
                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition == index, <---- делаем отмеченным 0 элемент
						onClick = { selectedItemPosition = index }, <--- если мы так делаем выбранный элемент НЕ ИЗМЕНИТСЯ! т.к. перерисовка NavigationBar не происходит
                        icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }
    ){
        Text(text = "Text", modifier = Modifier.padding(it))
    }
}
Рекомпозиция - 

@Composable
fun MainScreen(){
    Scaffold (
        bottomBar = {
            NavigationBar {
                log("NavigationBar")
                val selectedItemPosition = mutableStateOf(0) <---- заменяем на state. Для сохранения состояния используем remember
                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value == index, <---- похоже на LiveData, кладём новое значение, 
                        onClick = { selectedItemPosition.value = index }, <----- кладём новое значение и происходит Рекомпозиция,но State равен 0 всегда, состояние не запоминается
                        icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }
    ){
        Text(text = "Text", modifier = Modifier.padding(it))
    }
}

---------------
Запоминаем состояние
 val selectedItemPosition = remember {
                    mutableIntStateOf(0)
                }
				
1)Если Composable-функция зависит от какого-то стейта, то при изменении этого стейта, эта Composable-функция будет вызвана снова, произойдёт рекомпозиция
2) Чтобы стейт сохранился нужно вызывать remember
3) Изменени значений стейта через value

#3.4 FAB и SnackBar

Добавим FAB. По клику показывается SnackBar. В нём есть кнопка "Hide FAB", по клику по которой FAB становится невидимой

@Composable
fun MainScreen(){
    val snackBarHostState = SnackbarHostState() <----------- состояние SnackBar
    val fabIsVisible = remember { <---------- состояние видимости кнопки FAB
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope() <--------- Scope для Composable-функций
    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) <--- добавляем SnackBar и передаём State
        },
        floatingActionButton = { <-------- добавляем FAB
            if(fabIsVisible.value) { <--- в случае невидимости просто не добавляем FAB на экран и после рекомпозиции она будет не видна
                FloatingActionButton(onClick = {
                    scope.launch {
                        val action = snackBarHostState.showSnackbar( <---- suspend функция, может возвращать два состояния либо был скрыт, либо нажата кнопка
                            message = "This is snackBar",
                            actionLabel = "Hide FAB",
                            duration = SnackbarDuration.Long

                        )
                        if (action == SnackbarResult.ActionPerformed) { <--- при нажатии на кнопку меняем стейт видимости FAB
                            fabIsVisible.value = false
                        }
                    }

                }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                }
            }
        },
        bottomBar = {..............
	
##State и работа с ViewModel
	
#4.1 Рекомпозиция

Добавим лог

fun MainScreen(){
    val snackBarHostState = SnackbarHostState()

    log("snackBarHostState = ${snackBarHostState.currentSnackbarData.toString()}")
    val fabIsVisible = remember {
        mutableStateOf(true)
    }
	...
В итоге SnackBar не показывается!!!
Посмотрим изначальный код. У нас есть два состояния snackBarHostState и fabIsVisible и рекомпозиция происходит только у тех Composable-функций, 
которые зависят от этих состояний при их смене!!!
Когда мы прописываем log с состояние SnackBar - то MainScreen становится зависим от него и переживает рекомпозицию, при этом SnackbarHostState не переживает рекомпозицию и обнуляется!
Чтобы это исправить, нужно обновить его в remember
Поэтому всегда лучше использовать remember для стейтов, чтобы они умели пережить рекомпозицию экрана

#4.4 Слушатели клика

В MainScreen удаляем FAB и SnackBar

@Composable
fun MainScreen(){

    val scope = rememberCoroutineScope()
    Scaffold (
        bottomBar = {
            NavigationBar {

                val selectedItemPosition = remember {
                    mutableIntStateOf(0)
                }
                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
                        icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }

    ){
        Text(text = "Text", modifier = Modifier.padding(it))
        PostCard() <--- сюда не передать padding, поэтому хорошей практикой при написании Composable-функций является передача параметра Modifier
    }
}

@Composable
fun PostCard(modifier: Modifier = Modifier) { <--- передаём Modifier
    Card(
        modifier = modifier.fillMaxWidth(), <------- и этот modifier передаём в верхнюю функцию + расширяем если нужно
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {......
	
Теперь поработаем с панелью статистики

Чтобы не хардкодить, создадим класс, который будет представлять из себя один элемент статистики

enum class StatisticType{
    VIEWS,
    COMMENTS,
    SHARES,
    LIKES
}
data class StatisticItem(
    val type : StatisticType,
    val count : Int = 0
)

Передадим статистику

@Composable
private fun Statistics(statistics : List<StatisticItem>)
....
Напишем расширение для поиска элемента по типу

private fun List<StatisticItem>.getItemByType(type : StatisticType) : StatisticItem{
    return this.find { it.type == type } ?: throw IllegalArgumentException()
}

И

@Composable
private fun Statistics(statistics : List<StatisticItem>) {
    Row {
        Row(modifier = Modifier.weight(1f))
        {
            val viewItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(iconResId = R.drawable.ic_views_count, 
                viewItem.count.toString())
        }
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            var viewItem = statistics.getItemByType(StatisticType.SHARES)
            IconWithText(iconResId = R.drawable.ic_share,
                viewItem.count.toString())
            viewItem = statistics.getItemByType(StatisticType.COMMENTS)
            IconWithText(iconResId = R.drawable.ic_comment,
                viewItem.count.toString())
            viewItem = statistics.getItemByType(StatisticType.LIKES)
            IconWithText(iconResId = R.drawable.ic_like,
                viewItem.count.toString())
        }

    }
}


Создадим данные для Post с дефолтными значениями

data class FeedPost(
    val comunityName: String = "/dev/null",
    val publicationDate: String = "14:00",
    val avatarResId: Int = R.drawable.post_comunity_thumbnail,
    val contentText: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 234),
        StatisticItem(StatisticType.COMMENTS, 113),
        StatisticItem(StatisticType.SHARES, 34),
        StatisticItem(StatisticType.LIKES, 243)
    )
)

Передадим объект FeedPost в PostCard и заменим данные из класса данных

@Composable
fun PostCard(modifier: Modifier = Modifier, feedPost: FeedPost) { <--------------
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            PostHeader(feedPost) <----------------
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.contentText) <-----------------------
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = feedPost.contentImageResId), <-----------
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(feedPost.statistics)
        }

    }

}



@Composable
private fun PostHeader(feedPost: FeedPost) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            painter = painterResource(id = feedPost.avatarResId), <-------------
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(
                text = feedPost.comunityName, <-------------------
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feedPost.publicationDate, <------------------
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

Теперь поработаем с статистикой

@Composable
private fun IconWithText(iconResId: Int, text: String,
                         onItemClickListener : () -> Unit) {
    Row(
        modifier = Modifier.clickable {
            onItemClickListener()   <----клик на элемент Row передаём наверх
        },
		.................
		
В функции Statistics также клик пробрасываем вверх

private fun Statistics(statistics : List<StatisticItem>,
                       onItemClickListener: (item : StatisticItem) -> Unit) { <--- добавляем листенер
    Row {
        Row(modifier = Modifier.weight(1f))
        {
            val viewItem = statistics.getItemByType(StatisticType.VIEWS)
            IconWithText(iconResId = R.drawable.ic_views_count,
                viewItem.count.toString()
            ) { onItemClickListener(viewItem) } <--- пробрасываем клик
        }


В PostCard также пробрасываем

@Composable
fun PostCard(modifier: Modifier = Modifier, feedPost: FeedPost,
onStatisticItemClickListener: (item: StatisticItem) -> Unit <-------------------------------назовём более понятно
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {

        Column(modifier = Modifier.padding(8.dp)) {
            PostHeader(feedPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = feedPost.contentText)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = feedPost.contentImageResId),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(8.dp))
            Statistics(feedPost.statistics, onStatisticItemClickListener) <-------------------
        }

    }

}

Перекидываем clickListener выше и заменяем в одном элементе статистики count 
fun MainScreen()
.....
 PostCard(modifier = Modifier.padding(it),
            feedPost = feedPost.value,
            onStatisticItemClickListener = { newItem ->
                val oldStatistics = feedPost.value.statistics
                val newStatistics = oldStatistics.toMutableList().apply {
                    replaceAll{oldItem ->
                        if(oldItem.type == newItem.type){
                            oldItem.copy(count = oldItem.count + 1)
                        }else{
                            oldItem
                        }

                    }
                }
                feedPost.value = feedPost.value.copy(statistics = newStatistics.toList())
            }
        )
		
#4.5 Добавление ViewModel

class MainViewModel : ViewModel() {

    private val _feedPost = MutableLiveData(FeedPost())

    val feedPost : LiveData<FeedPost> = _feedPost

    public fun updateStatistics(item: StatisticItem){
        val oldStatistics = feedPost.value.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if(oldItem.type == item.type){
                    oldItem.copy(count = oldItem.count + 1)
                }else{
                    oldItem
                }
            }
        }
        _feedPost.value = feedPost.value.copy(statistics = newStatistics)
    }
}

В MainActivity :


val viewModel : MainViewModel by viewModels<MainViewModel>()

@Composable
fun MainScreen(viewModel: MainViewModel){
......

    ){
        val feedPost = viewModel.feedPost.observeAsState(initial = FeedPost())        <-- feedPost переносим сюда
        PostCard(modifier = Modifier.padding(it),
            feedPost = feedPost.value,
            onStatisticItemClickListener = { newItem ->
               viewModel.updateStatistics(newItem)
            }
        )
    }
}

Но далее будет отдельная функция для каждого элемента статистики, поэтому разделим их

{
        val feedPost = viewModel.feedPost.observeAsState(initial = FeedPost())
        PostCard(modifier = Modifier.padding(it),
            feedPost = feedPost.value,
            onViewsClickListener = {
                                      viewModel.updateStatistics(it)
            },
            onShareClickListener = {
                viewModel.updateStatistics(it)
            },
            onCommentsClickListener = {
                viewModel.updateStatistics(it)
            },
            onLikeClickListener = {
                viewModel.updateStatistics(it)
            }
        )
    }
	
Здесь можно использовать метод-референс

{ paddingValues ->
        val feedPost = viewModel.feedPost.observeAsState(initial = FeedPost())
        PostCard(modifier = Modifier.padding(paddingValues),
            feedPost = feedPost.value,
            onViewsClickListener = viewModel::updateStatistics,           
            onShareClickListener = viewModel::updateStatistics,
            onCommentsClickListener = viewModel::updateStatistics,
            onLikeClickListener =viewModel::updateStatistics
        )
    }
	
#4.9 Добавляем LazyColumn в VkClient

Реализация

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(100){
            add(FeedPost(it))
        }
    }
    private val _feedPostList = MutableLiveData( initList.toList())

    val feedPostList : LiveData<List<FeedPost>> = _feedPostList

    private fun List<FeedPost>.getItemById(id: Int) : FeedPost{
        return this.find{it.id == id} ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost){
        val oldList = _feedPostList.value?.toMutableList() ?: mutableListOf()
        oldList.remove(feedPost)
        _feedPostList.value = oldList
    }

    public fun updateStatistics(feedPost: FeedPost ,statisticItem: StatisticItem){
        val feedPostItem = _feedPostList.value?.getItemById(feedPost.id) ?: throw IllegalArgumentException("FeedPost not found!")
        val oldStatistics = feedPostItem.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if(oldItem.type == statisticItem.type){
                    oldItem.copy(count = oldItem.count + 1)
                }else{
                    oldItem
                }
            }
        }
        //val newFeedPostItem = feedPostItem.copy(statistics = newStatistics)
        val newFeedPostList = _feedPostList.value?.toMutableList()?.apply {
            replaceAll { oldItem ->
                if(oldItem.id == feedPost.id){
                    oldItem.copy(statistics = newStatistics)
                }else{
                    oldItem
                }

            }
        }
        _feedPostList.value = newFeedPostList

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel){


    Scaffold (
        bottomBar = {
            NavigationBar {

                val selectedItemPosition = remember {
                    mutableIntStateOf(0)
                }
                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
                        icon = {
                        Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondary)
                    )
                }
            }
        }

    ){ paddingValues ->
        val feedPostList = viewModel.feedPostList.observeAsState(initial = listOf())
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(feedPostList.value, key = {it.id }){ feedPost ->
                val dismissState = rememberDismissState()  <--- state Обязательно должен быть внутри item!

                if(dismissState.isDismissed(DismissDirection.EndToStart)){
                    viewModel.deleteItem(feedPost)
                }
                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        Box(modifier = Modifier.padding(16.dp)
                            .background(color = Color.Red.copy(alpha = 0.5f))
                            .fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ){
                            Text(text = "Delete Item",
                                modifier = Modifier.padding(16.dp),
                                color = Color.White)
                        }
                    },
                    dismissContent = {
                        PostCard(modifier = Modifier.padding(16.dp),
                            feedPost = feedPost,
                            onViewsClickListener = { statisticItem ->
                                viewModel.updateStatistics(feedPost,statisticItem) },
                            onShareClickListener = { statisticItem ->
                                viewModel.updateStatistics(feedPost,statisticItem) },
                            onCommentsClickListener = { statisticItem ->
                                viewModel.updateStatistics(feedPost,statisticItem) },
                            onLikeClickListener = { statisticItem ->
                                viewModel.updateStatistics(feedPost,statisticItem) }
                        )

                    },
                    directions = setOf(DismissDirection.EndToStart))

            }
        }

    }
}

Cвойство paddingValue в scafold на самом деле полезная штука. 
Оно как раз решает проблему скрытости контента за нижнем меню, и данное 
свойство появляется только тогда, когда мы устанавливаем нижнее меню. 
То есть при установке paddingValues(it) больше не нужно высчитывать dp что 
бы контент не заползал за нижнее меню, это свойство высчитывает это автоматом, 
каким бы по высоте не было бы нижнее меню. Надеюсь коммент был полезен

Уберём padding в FeedPost и установим падденги внутри LazyColumn

LazyColumn(modifier = Modifier.padding(paddingValues), <--- paddingValue из Scafold
            contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp) <--- content
			verticalArrangement = Arrangement.spacedBy(16.dp) <--- отступ между элементами
            )
			
Добавим анимацию при удалении

SwipeToDismiss(
               modifier = Modifier.animateItemPlacement(), <--- просто добавим анимацию в modifier  объекта SwipeToDismiss
			   ......