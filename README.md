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
			   
			   
#5. Навигация в Jetpack Compose

#5.1 Навигация без использования библиотек

Перенесём selectedNavItem во ViewModel

class MainViewModel : ViewModel() {

    ........
    
    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem : LiveData<NavigationItem> = _selectedNavIt
	
	fun selectNavItem(navigationItem: NavigationItem){
        _selectedNavItem.value = navigationItem
    }
	
	.........
	
Меняем view:

@Composable
fun MainScreen(viewModel: MainViewModel){

    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home) <------
    Scaffold (
        bottomBar = {
            NavigationBar {


                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                items.forEachIndexed { index, item -> <----- индесы можно удалить
                    NavigationBarItem(
                        selected = selectedNavItem == item, <------
                        onClick = { viewModel.selectNavItem(item) }, <-------
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
		........
		
Перенесём содержимое Scaffold в отдельный файли в отдельную Composable-функцию

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel : MainViewModel,
    paddingValues : PaddingValues
){

    val feedPostList = viewModel.feedPostList.observeAsState(initial = listOf())
    LazyColumn(modifier = androidx.compose.ui.Modifier.padding(paddingValues),
        contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(feedPostList.value, key = {it.id }){ feedPost ->
            val dismissState = rememberDismissState()

            if(dismissState.isDismissed(DismissDirection.EndToStart)){
                viewModel.deleteItem(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {
                    Box(modifier = Modifier
                        .padding(16.dp)
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
                    PostCard(modifier = Modifier,
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

Внутрь Scaffold прописываем
...
{ paddingValues ->
        when(selectedNavItem){
            NavigationItem.Home -> HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
            NavigationItem.Favorite -> Text(text = "Favorite", color = Color.Black)
            NavigationItem.Profile -> Text(text = "Profile", color = Color.Black)
        }

    }
	....
	
Всё работает, но есть несколько проблему
1) Нет бэкстека
2) Состояние вкладки не сохраняется
Выведем кол-во нажатий на экран

@Composable
fun TextCounter(text : String){
    var count by remember {
        mutableStateOf(0)
    }
    Text(modifier = Modifier.padding(16.dp)
        .clickable { count++ },
        text = "Name = $text count = $count", 
        color = MaterialTheme.colorScheme.onPrimary)
}

...
{ paddingValues ->
        when(selectedNavItem){
            NavigationItem.Home -> HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
            NavigationItem.Favorite -> TextCounter(text = "Favorite")
            NavigationItem.Profile -> TextCounter(text = "Profile")
        }

    }
	....
При повторном переходе во вкладу (Favorite или Profile) count не сохраняется, т..к. Composable-функция каждый раз вызывается занова


#5.2 Jetpack Compose Navigation

Добавляем библиотеку

implementation("androidx.navigation:navigation-compose:2.8.6")

Путь - является строкой
Создадим пакет navigation класс Screen - это все экраны нашего приложения, каждый хранит путь, который будем использовать для навигации

sealed class Screen(val route: String) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVORITE)
    object Profile : Screen(ROUTE_PROFILE)


    private companion object {
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"
    }
}

НАВИГАЦИЯ представляет собой граф, в котором прописываются все возможные переходы. Его нужно создать
Навигация - это Composable-функция, создаём её в одноимённом файле!

@Composable
fun AppNavGraph(navHostController: NavHostController){ <--- пока передадим в функцию
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeed.route <-- начальный путь
        ){// в билдере будем строить сам граф и добавлять destination
        composable<>()
    }

}

public fun NavGraphBuilder.composable( <- composable под кпотом
    route: String,
    .....
) {
    destination( <--- добавляет направление
	....

Далее

@Composable
fun AppNavGraph(navHostController: NavHostController,
                homeScreenContent : @Composable () -> Unit
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeed.route
        ){
        composable(Screen.NewsFeed.route){ <-- Передаём название пути, аргументы для экрана
            homeScreenContent				<--- И контент , что показываем при переходе, но выносим в аргумент функции AppNavGraph
        }
    }

}

Аналогично остальные экраны

@Composable
fun AppNavGraph(navHostController: NavHostController, <--- стейт навигации
                homeScreenContent : @Composable () -> Unit,
                favoriteScreenContent : @Composable () -> Unit,
                profileScreenContent : @Composable ()-> Unit
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeed.route
        ){
        composable(Screen.NewsFeed.route){
            homeScreenContent()
        }
        
        composable(Screen.Favorite.route){
            favoriteScreenContent()
        }

        composable(Screen.Profile.route){
            profileScreenContent()
        }
    }

}
Граф готов, можно использовать его на главном экране

В MainScreen

val navHostController = rememberNavController()

и вместо

  when(selectedNavItem){
            NavigationItem.Home -> HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
            NavigationItem.Favorite -> TextCounter(text = "Favorite")
            NavigationItem.Profile -> TextCounter(text = "Profile")
        }
создаём граф

 AppNavGraph(
            navHostController = navHostController,
            homeScreenContent = {
                HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
                                },
            favoriteScreenContent = {
                TextCounter(text = "Favorite")
            },
            profileScreenContent = {
                TextCounter(text = "Profile")
            })
			
Но BottomNavigation ничего не знает про наш граф и его элементы никак не связаны с нашей навигацией

1) Добавим в NavigationItem поле Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId : Int,
    val icon: ImageVector
){
    object Home : NavigationItem (Screen.NewsFeed,R.string.navigation_item_main, Icons.Outlined.Home)
    object Favorite : NavigationItem (Screen.Favorite,R.string.navigation_item_favourite, Icons.Outlined.Favorite)
    object Profile : NavigationItem (Screen.Profile,R.string.navigation_item_profile, Icons.Outlined.Person)
}

Убираем связь с selectedItemPosition из ViewModel

fun MainScreen(viewModel: MainViewModel){
....
				val navBackStackEntry by navHostController.currentBackStackEntryAsState() <--- текушее состояние стека
                val currentRoute = navBackStackEntry?.destination?.route <--- из него берём текущий route
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route, <---- сравниваем с текущим route
                        onClick = {
                            navHostController.navigate(item.screen.route) <---- по клику пользуемся навигацией
                                  },
								  
Запускаем - бэкстек работает, остальные проблемы дальше

#5.3 Исправляем баги в навигации

В прошлом уроке мы реализовали переходы по экраном с помощью библиотеки Jetpack Compose Navigation
Но ещё есть ряд проблему


1)При переходе назад не сохраняется кол-во кликов в Favorite. При этом мы не создавали занова Composable-функцию!!! 
Она была взята из бэкстека, но при этом её состояние сбросилось. При этом стейт первого списка сохранился
Решение: Нужно использовать функцию rememberSaveable
При попадании в бэкстек Composable-функция умирает,а при нажатии Назад пересоздаётся. При использовании remember состояние не сохраняется

Теперь стейт сохраняется. Почему же сохраняется на первом экране
- Если посмотреть LazyColumn то в нём на скрол по-умолчанию повешено savable состояние

2) Если кликнуть несколько раз на один и тот же элемент,то будет создано множество одинаковых Composable-функций
Решение:

Смотрим  navHostController.navigate(item.screen.route)

 @MainThread
    public fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) {
        navigate(route, navOptions(builder))
    }
	
public class NavOptionsBuilder {
    private val builder = NavOptions.Builder()

    /**
     * Whether this navigation action should launch as single-top (i.e., there will be at most one
     * copy of a given destination on the top of the back stack).
     *
     * This functions similarly to how [android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP] works with
     * activities.
     */
    public var launchSingleTop: Boolean = false
	.....
Решено! 

onClick = {
                                navHostController.navigate(item.screen.route){                                   
                                    launchSingleTop = true
                                }
								
3) При множестве переходов между экранами хранится весь бэкстек

Здесь несколько решений. Лучшее от google - из бэкстека удаляются все экраны между тем который перешли и стартовым
Пример: Главная -> Избранное -> Профиль. При переходе на третий экран (Профиль) из бэкстека удалится Избранное. При клике Назад пользователь перейдёт к стартовому экрану
Реализуем это

					onClick = {
                                navHostController.navigate(item.screen.route){
                                    popUpTo(Screen.NewsFeed.route)
                                    launchSingleTop = true
                                }
4) При решении проблемы 3, не сохоаняются состояния экранов count
Решение:

 onClick = {
                                navHostController.navigate(item.screen.route){
                                    popUpTo(Screen.NewsFeed.route){
                                        saveState = true <-- сохранять при закрытии/удалении из бэкстека
                                    }
                                    launchSingleTop = true
                                    restoreState = true <--- восстановление состояния
                                }
								
Обратить внимание!! Стейт не сохраниться если экран удалить из бэкстека нажав Назад!

#5.4 Рефакторинг навигации. RememberNavigationState
Сейчас навигация работает корректно, но при этом реализована полностью в MainScreen
Поэтому вынесем работу с navController в отдельный класс


class NavigationState(val navHostController: NavHostController) {
    
    fun navigateTo(route : String){
        navHostController.navigate(route){
            popUpTo(Screen.NewsFeed.route){ <--- заменяем на popUpTo(navHostController.graph.startDestinationId)
                saveState = true
            }
            launchSingleTop = true
            restoreState = true 
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel){

    val navController = rememberNavController() <----
    
    val navigationState = remember { <----- remember ==  этот объект не пересоздаётся при рекомпозиции!!
        NavigationState(navController)
    }
  
    Scaffold (
        bottomBar = {
            NavigationBar {


                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState() <----
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {
                                    navigationState.navigateTo(item.screen.route) <----
                                  },
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
        
        AppNavGraph(
            navHostController = navigationState.navHostController, <----
            homeScreenContent = {
                HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
                                },
            favoriteScreenContent = {
                TextCounter(text = "Favorite")
            },
            profileScreenContent = {
                TextCounter(text = "Profile")
            })
    }
}

Но это ещё не всё! Далее

В файле NavigationState создаём функцию

@Composable
fun rememberNavigateState(
    navHostController: NavHostController = rememberNavController()
) : NavigationState{
    return remember {
        NavigationState(navHostController)
    }
}

И в MainScreen меняем

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel){

    val navigationState = rememberNavigateState()
	.....
	
Документация:

/**
 * Remember the value produced by [calculation]. [calculation] will only be evaluated during the
 * composition. Recomposition will always return the value produced by composition.
 */
@Composable
inline fun <T> remember(crossinline calculation: @DisallowComposableCalls () -> T): T =
    currentComposer.cache(false, calculation)
	
	
	#5.5 Создание экрана комментариев. Часть 1
	
	Создадим экран с комментариями. Навигацию реализуем без использования библиотеки
	
	Нам понадобится класс, который представляет собой комментарий
	
data class PostComment(
    val id: Int,
    val authorName : String = "Author Name",
    val authorAvatarId : Int = R.drawable.comment_author_avatar,
    val commentTeat : String = "Long comment text",
    val publicationDate: String = "14:00"

)

Теперь создадим экран c комментариями

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    feedPost: FeedPost,
    comments: List<PostComment>
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Comments for FeedPost Id : ${feedPost.id}")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack  , contentDescription = null )

                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn (modifier = Modifier.padding(paddingValues)
		 contentPadding = PaddingValues(
                top = 16.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 72.dp
            )
			) {
            items(comments, key = {it.id }){
                CommentItem(comment = it)
            }
        }

    }
}
// Отдельная функция для одного комментария
@Composable
private fun CommentItem(comment: PostComment){
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)){
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = comment.authorAvatarId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${comment.authorName} CommentId: ${comment.id}",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.commentText,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.publicationDate,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 12.sp)
        }
    }
}

@Preview
@Composable
private fun commentPreview(){
    VkNewsClientTheme {
        CommentItem(comment = PostComment(0))
    }
}

Тестировать будем в HomeScreen

fun HomeScreen(
    viewModel : MainViewModel,
    paddingValues : PaddingValues
){

    val feedPostList = viewModel.feedPostList.observeAsState(initial = listOf())
    val comments = mutableListOf<PostComment>().apply {
        repeat(20){
            add(PostComment(it))
        }
    }
    CommentsScreen(feedPost = feedPostList.value[0],comments)
	....
	//остальное пока закомментируем
	
	
#5.6 UDF и создание стейта

Вернём HomeScreen - список постов

Получается на HomeScreen может быть два состояния - Список постов и Список комментариев
Плохое решение - добавить в ViewModel  список комментариев  и добавить ещё один стейт в HomeScreen
Почему, - потому что одна Composable-функция MainScreen будет зависеть от двух разных состояний и нужно будет писать очень сложную логику
Поэтому используется UDF-архитектура
Unidirectinal Data Flow - однонаправленный поток данных
Все события передаём ViewModel-из, ViewModel меняет стейт и отдаёт его Composable-функции

Создадимотдельный класс, который будет представлять собой состояние экрана

sealed class HomeScreenState {

    data class Posts(val posts : List<FeedPost>) : HomeScreenState()
    data class Comments(val feedPost: FeedPost, val comments : List<PostComment>) : HomeScreenState()
}

Теперь обновим ViewModel

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(FeedPost(it))
        }
    }

    private val initState = HomeScreenState.Posts(initList.toList())
    private val _screenState = MutableLiveData<HomeScreenState>(initState)

    val screenState : LiveData<HomeScreenState> = _screenState
.... остальное исправим позже

@Composable
fun HomeScreen(
    viewModel : MainViewModel,
    paddingValues : PaddingValues
){

    val screenState = viewModel.screenState.observeAsState() <--- так мы можем иметь значение null. Хорошей практикой является создать Default state object Initial : HomeScreenState()

    when(val currentState = screenState.value){
        is HomeScreenState.Posts -> {
            FeedPosts(posts = currentState.posts, viewModel = viewModel, paddingValues = paddingValues )
        }
        is HomeScreenState.Comments ->{
            CommentsScreen(feedPost = currentState.feedPost, comments = currentState.comments)
        }

    }


}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: MainViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>
){
    LazyColumn(modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts, key = {it.id }){ feedPost ->
            val dismissState = rememberDismissState()

            if(dismissState.isDismissed(DismissDirection.EndToStart)){
                viewModel.deleteItem(feedPost)
            }
            SwipeToDismiss(
                modifier = Modifier.animateItemPlacement(),
                state = dismissState,
                background = {
                    Box(modifier = Modifier
                        .padding(16.dp)
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
                    PostCard(modifier = Modifier,
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

Продолжим в следующем уроке

#5.7 Навигация на экран комментариев

Доработаем ViewModel

class MainViewModel : ViewModel() {

    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(FeedPost(it))
        }
    }

    private val initState = HomeScreenState.Posts(initList.toList())
    private val _screenState = MutableLiveData<HomeScreenState>(initState)

    val screenState : LiveData<HomeScreenState> = _screenState

    private fun List<FeedPost>.getItemById(id: Int) : FeedPost{
        return this.find{it.id == id} ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost){
        val currentState = screenState.value
        if(currentState !is HomeScreenState.Posts){
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(oldPosts)
    }

    public fun updateStatistics(feedPost: FeedPost ,statisticItem: StatisticItem){
        val currentState = screenState.value
        if(currentState !is HomeScreenState.Posts){
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        val feedPostItem = oldPosts.getItemById(feedPost.id) 
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
        val newFeedPostList = oldPosts.apply {
            replaceAll { oldItem ->
                if(oldItem.id == feedPost.id){
                    oldItem.copy(statistics = newStatistics)
                }else{
                    oldItem
                }

            }
        }
        _screenState.value = HomeScreenState.Posts(newFeedPostList)

    }
}

Теперь при клике на комментарий нужно открывать пост с комментариями

Допишем ViewModel

class MainViewModel : ViewModel() {

    private val comments = mutableListOf<PostComment>().apply {
        repeat(20){
            add(PostComment(it))
        }
    }

   ......

    fun showComments(feedPost: FeedPost){
        _screenState.value = HomeScreenState.Comments(feedPost, comments)
    }
	....
	
И вызовем showComments по клику на комменатрий в HomeScreen
Всё работает
Есть 2 проблемы
1) Кнопка назад в комментарии
2) Back системная закрывает приложение

fun CommentsScreen(
    feedPost: FeedPost,
    comments: List<PostComment>,
    onBackPressed : () -> Unit <--- перекинем наверх
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Comments for FeedPost Id : ${feedPost.id}")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed() <------------
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack  , contentDescription = null )

                    }
                }
            )
        }
    )
	
	..............
	
class MainViewModel : ViewModel() {

    
    val screenState : LiveData<HomeScreenState> = _screenState

    private var savedState :HomeScreenState? = initState <--- храним предыдущее состояние

    fun showComments(feedPost: FeedPost){
        savedState = _screenState.value <---- save previos
        _screenState.value = HomeScreenState.Comments(feedPost, comments)
    }

    fun closeComments(){ <---- возвращаемся к предыдущему состоянию
        _screenState.value = savedState
    }
	.....................
	
	
fun HomeScreen(
    viewModel : MainViewModel,
    paddingValues : PaddingValues
){

    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    when(val currentState = screenState.value){
        is HomeScreenState.Posts -> {
            FeedPosts(posts = currentState.posts, viewModel = viewModel, paddingValues = paddingValues )
        }
        is HomeScreenState.Comments ->{
            CommentsScreen(feedPost = currentState.feedPost, comments = currentState.comments){
                viewModel.closeComments() <--- close при нажатии на кнопу на экране
            }
            BackHandler { <--- обработка системной кнопки Back только в состоянии Comments!!!
                viewModel.closeComments()
            }
        }
        HomeScreenState.Initial -> {

        }
    }


}

Есть проблема - не сохраняется state первого экрана, т.к. Composable-функция пересоздаётся. Этот момент исправим в будущем!

#5.8 Рефакторинг VIewModels

Переработаем навигацию к комментариям через библиотеку навигации
Разделим ViewModel - и для двух экранов - для News и для Comments

Переименуем MainViewModel в NewsFeedViewModel и уберём функциональность с комментариями .
Также переименуем стейт

class NewsFeedViewModel : ViewModel() {

  
    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(FeedPost(it))
        }
    }

    private val initState = NewsFeedScreenState.Posts(initList.toList())
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initState)

    val screenState : LiveData<NewsFeedScreenState> = _screenState

    
    private fun List<FeedPost>.getItemById(id: Int) : FeedPost{
        return this.find{it.id == id} ?: throw IllegalArgumentException("FeedPost with id = $id not found!")
    }

    fun deleteItem(feedPost: FeedPost){
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts){
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(oldPosts)
    }

    public fun updateStatistics(feedPost: FeedPost ,statisticItem: StatisticItem){
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts){
            return
        }
        val oldPosts = currentState.posts.toMutableList()
        val feedPostItem = oldPosts.getItemById(feedPost.id)
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
        val newFeedPostList = oldPosts.apply {
            replaceAll { oldItem ->
                if(oldItem.id == feedPost.id){
                    oldItem.copy(statistics = newStatistics)
                }else{
                    oldItem
                }

            }
        }
        _screenState.value = NewsFeedScreenState.Posts(newFeedPostList)

    }
}

Для страницы комментариев создадим свой стейт и свою ViewModel


sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()
    data class Posts(val posts : List<FeedPost>) : NewsFeedScreenState()
}

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()
    data class Comments(val feedPost: FeedPost, val comments : List<PostComment>) : CommentsScreenState()
}

class CommentsViewModel : ViewModel() {

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)

    val screenState : LiveData<CommentsScreenState> = _screenState

    init{
        loadComments(FeedPost(0)) <--- временное решение для проверки работоспособности
    }
    fun loadComments(feedPost: FeedPost){
        val comments = mutableListOf<PostComment>().apply {
            repeat(20){
                add(PostComment(it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost, comments)
    }

}

Теперь ViewModel-и нужно передать в соответствующие Composable-функции
Один из вариантов загрузить их в MainActivity, но со временем их может стать очень много, поэтому это неудобно. Это плохое решение.
На самом деле есть возможность создать экземпляр ViewModel внутри Composable-функции

Создадим экземляр NewsFeedViewModel прямо внутри HomeScreen:
 !!!!  val viewModel = ViewModelProvider(LocalViewModelStoreOwner.current!!)[NewsFeedViewModel::class.java] 
 
 Но получается довольно громостко, и есть способо намного проще. Для начала нужно подключить библиотеку
 
 "androidx.lifecycle::lifecycle-viewmodel-compose:2.5.1
 
 Теперь создать ViewModel можно так
 
 val viewModel : NewsFeedViewModel = viewModel() <--- самое главное указать тип ViewModel-и
 
 
 

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    onBackPressed : () -> Unit
){
    val viewModel: CommentsViewModel = viewModel() 
    val screenState = viewModel.screenState.observeAsState(initial = CommentsScreenState.Initial)
    val currentState = screenState.value
    if(currentState is CommentsScreenState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Comments for FeedPost Id : ${currentState.feedPost.id}")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)

                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                )
            ) {
                items(currentState.comments, key = { it.id }) {
                    CommentItem(comment = it)
                }
            }

        }
    }
}

Пока мы пишем так,чтобы приложение было работоспособным
В MainScreen добавим стейт - commentsToPost: Если он нулевой показываем посты, если нет - комментарии

@Composable
fun MainScreen(){

    val navigationState = rememberNavigateState()

    val commentsToPost = remember { <-------------------
        mutableStateOf<FeedPost?>(null)
    }

    Scaffold (
        bottomBar = {
            NavigationBar {


                val items = listOf(NavigationItem.Home,NavigationItem.Favorite,NavigationItem.Profile)
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {
                                    navigationState.navigateTo(item.screen.route)
                                  },
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
        
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = { <------------------
                if(commentsToPost.value == null) {
                    HomeScreen(paddingValues = paddingValues) {
                        commentsToPost.value = it <-------------
                    }
                }else{
                    CommentsScreen {
                        commentsToPost.value = null <----------------
                    }
                }
                BackHandler {
                    commentsToPost.value = null <---------------
                }
            },
            favoriteScreenContent = {
                TextCounter(text = "Favorite")
            },
            profileScreenContent = {
                TextCounter(text = "Profile")
            })
    }
}

Продолжение в следующем уроке....
 
 #5.9 Передача параметров во вью-модель. ViewModelFactory
 
 Нужно передать feedPost в CommentsViewModel
 
 Если делать вот так
 
 @Composable
fun CommentsScreen( <-- зависит от стейта ViewModel-и а его стейт меняет функция loadComments
    onBackPressed : () -> Unit
){
    val viewModel: CommentsViewModel = viewModel()
    
    viewModel.loadComments() 
	
То после загрузки и смены состояния будет происходить рекомпозиция и функция loadComments будет вызываться занова - Side Effect!!

Вот так  viewModel.loadComments(FeedPost()) загрузка будет конечной, т.к. стейт постоянный, т.к. FeedPost является data классом и у него переопределены equal и hash
А если FeedPost сделать не data классом, то стейт будет меняться и загрузка/рекомпозиция зациклятся

ПОЭТОМУ ТАК ДЕЛАТЬ НЕ СТОИТ
РЕШЕНИЕ:

Передадим FeedPost прямо в конструктор ViewModel-и

class CommentsViewModel(val feedPost: FeedPost) : ViewModel() { <--------------------

    private val _screenState = MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)

    val screenState : LiveData<CommentsScreenState> = _screenState

    init{
        loadComments(feedPost) <----------------
    }
    private fun loadComments(feedPost: FeedPost){
        val comments = mutableListOf<PostComment>().apply {
            repeat(20){
                add(PostComment(it))
            }
        }
        _screenState.value = CommentsScreenState.Comments(feedPost, comments)
    }

}

Далее FeedPost передадим как параметр в CommentsScreen

@Composable
fun CommentsScreen(
    onBackPressed : () -> Unit,
    feedPost: FeedPost <---------------
){
....
И тепепрь передадим его во ViewModel через ViewModelFactory

Создадим вспомогательный класс CommentsViewModalFactory

class CommentsViewModalFactory(private val feedPost: FeedPost) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}

Создаём ViewModel c параметром:

fun CommentsScreen(
    onBackPressed : () -> Unit,
    feedPost: FeedPost
){
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModalFactory(feedPost)
    )
	.....
	
}

передаём параметр в CommentsScreen

 CommentsScreen (
                        onBackPressed = {commentsToPost.value = null},
                        feedPost = commentsToPost.value!!
                    )
					
Рефакторинг и подготовка закончены, в след.уроке начнём делать навигацию к экрану Comments с использованием Jetpack Compose Navigation

Из комментаривев к уроку:

Проблема: При открытии комментариев всегда вызывается один и тот же FeedPost, который инициализирован один раз
Решение :

@Composable
fun CommentsScreen(
    onBackPressed : () -> Unit,
    feedPost: FeedPost
){
    val viewModel: CommentsViewModel = viewModel(
        factory = CommentsViewModalFactory(feedPost)
    )

    val screenState = viewModel.screenState.observeAsState(initial = CommentsScreenState.Initial)
    val currentState = screenState.value

    val viewModelCurentState = LocalViewModelStoreOwner.current?.viewModelStore <----
    if(currentState is CommentsScreenState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Comments for FeedPost Id : ${currentState.feedPost.id}")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModelCurentState?.clear() <---- чистить стейт если он есть!
                            onBackPressed()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)

                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                )
            ) {
                items(currentState.comments, key = { it.id }) {
                    CommentItem(comment = it)
                }
            }

        }
    }
}


#5.10 Nested Graph Navigation
Получается в первой вкладке будет свой граф навигации - вложенная навигация

Добавляем скрины

sealed class Screen(val route: String) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVORITE)
    object Profile : Screen(ROUTE_PROFILE)
    
    object Home : Screen(ROUTE_HOME) <---------------- вложенный граф навигации, в котором будет два экрана NEWS_FEED и COMMENTS
    object Comments : Screen(ROUTE_COMMENTS) <--------------


    private companion object {
        const val ROUTE_HOME = "home" <--------------
        const val ROUTE_COMMENTS = "comments"    <--------------     
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"
       
    }
}

Идём в AppNavGraph

@Composable
fun AppNavGraph(navHostController: NavHostController,
                homeScreenContent : @Composable () -> Unit,
                favoriteScreenContent : @Composable () -> Unit,
                profileScreenContent : @Composable ()-> Unit,
                commentScreenContent : @Composable () -> Unit <---------------
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeed.route
        ){
 //       composable(Screen.NewsFeed.route){
 //               homeScreenContent()
 //           }
        
        navigation( <--- вместо composable используем navigation!
            startDestination = Screen.NewsFeed.route, //стартовый экрна в новом графе навигации
            route = Screen.Home.route				//имя вложенного графа навигации
        ){<------------------- билдер = экраны во вложенном графе навигации
            composable(Screen.NewsFeed.route){
                homeScreenContent()
            }
            composable(Screen.Comments.route){
                commentScreenContent() <--- передаём в качестве параметра
            }
        }

        composable(Screen.Favorite.route){
            favoriteScreenContent()
        }

        composable(Screen.Profile.route){
            profileScreenContent()
        }
    }

}

Обычно если несколько графов навигации, то каждый из них выводится в отдельную функцию

Переносим navigation в отдельную extantion функцию


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(Screen.Comments.route) {
            commentsScreenContent()
        }
    }
}

AppNavGraph будет выгладеть так

@Composable
fun AppNavGraph(navHostController: NavHostController,
                newsFeedScreenContent : @Composable () -> Unit,
                favoriteScreenContent : @Composable () -> Unit,
                profileScreenContent : @Composable ()-> Unit,
                commentsScreenContent : @Composable () -> Unit
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeed.route <---------------- позже надо будет поменять
        ){


       homeScreenNavGraph(
           newsFeedScreenContent = newsFeedScreenContent,
           commentsScreenContent = commentsScreenContent)

        composable(Screen.Favorite.route){
            favoriteScreenContent()
        }

        composable(Screen.Profile.route){
            profileScreenContent()
        }
    }

}

Теперь в VkNewsMainScreen правим граф

 AppNavGraph(
            navHostController = navigationState.navHostController,
            newsFeedScreenContent = {
                HomeScreen(paddingValues = paddingValues) {
                    commentsToPost.value = it
                    navigationState.navigateTo(Screen.Comments.route) <---- вызываем comment screen
                }

            },
            commentsScreenContent = {
                CommentsScreen(
                    onBackPressed = { commentsToPost.value = null },
                    feedPost = commentsToPost.value!!
                )
            },
            favoriteScreenContent = {
                TextCounter(text = "Favorite")
            },
            profileScreenContent = {
                TextCounter(text = "Profile")
            })
    }
 Запускаем в таком виде и приложение падает с ошибкой
 
 java.lang.IllegalArgumentException: navigation destination news_feed is not a direct child of this NavGraph
 
 Нужно исправить startDestination = Screen.Home.route
 
 Сейчасмножество багов , с ними будем разбираться дальше
 
 #5.11 Fix navigation bugs
 
 1) Если находимся на экране комментариев,то все иконки табов неактивные, т.к.
 
  NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
                        },
	а текущий route = "comments"
	Решение:
	
sealed class NavigationItem(
    val screen: Screen,
    val titleResId : Int,
    val icon: ImageVector
){
    object Home : NavigationItem (Screen.NewsFeed,R.string.navigation_item_main, Icons.Outlined.Home) <--- меняем на Screen.Home
    object Favorite : NavigationItem (Screen.Favorite,R.string.navigation_item_favourite, Icons.Outlined.Favorite)
    object Profile : NavigationItem (Screen.Profile,R.string.navigation_item_profile, Icons.Outlined.Person)
}

Далее. У пункта "Главная" свой вложенный граф. "Главная" нужно считать отмеченным, если экран, который сейчас отображается, находится в той же иерархии, что и route, 
который закреплён за данным элементом списка 

 Scaffold(
        bottomBar = {
            NavigationBar {
                val items =
                    listOf(NavigationItem.Home, NavigationItem.Favorite, NavigationItem.Profile)
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState() <--- состояние стека на верху
               
                items.forEachIndexed { index, item ->
//                    log("item = ${item.screen.route}")
//                    navBackStackEntry?.destination?.hierarchy?.forEach { <---hierarchy текущего состояния
//                        log("hierarchy item = ${it.route}")
//                    }
                    val selected = navBackStackEntry?.destination?.hierarchy?.any { <---------------------
                        it.route == item.screen.route                              <----------------------
                    } ?: false
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navigationState.navigateTo(item.screen.route)
                        },
						.............
Иерархия:

Наш граф  				Home				Favorite				Profile
				NewsFeed	Comments 
				
Для состояния NewsFeed hierarchy = [NewsFeed , Home, null]
Для состояния comments  = [Comments, Home, null]
Для Home = [Home, null]
Для Favorite = [Favorite, null]

/**
         * Provides a sequence of the NavDestination's hierarchy. The hierarchy starts with this
         * destination itself and is then followed by this destination's [NavDestination.parent],
         * then that graph's parent, and up the hierarchy until you've reached the root navigation
         * graph.
         */
        @JvmStatic
        public val NavDestination.hierarchy: Sequence<NavDestination>
            get() = generateSequence(this) { it.parent }
			
2) Переход на стартовый экран

class NavigationState(val navHostController: NavHostController) {

    fun navigateTo(route : String){
        navHostController.navigate(route){
            popUpTo(navHostController.graph.startDestinationId){ <--- проблема здесь, так как стартовый экран является вложенным. 
                saveState = true									//Этот метод работает только если стартовым является простой экран, а не граф
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

Меняем на 

class NavigationState(val navHostController: NavHostController) {

    fun navigateTo(route : String){
        navHostController.navigate(route){
            popUpTo(navHostController.graph.findStartDestination().id){ <----------
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

Если перейти в реализацию

 /**
         * Finds the actual start destination of the graph, handling cases where the graph's
         * starting destination is itself a NavGraph.
         *
         * @return the actual startDestination of the given graph.
         */
        @JvmStatic
        public fun NavGraph.findStartDestination(): NavDestination = childHierarchy().last()
		
3) При нажатии на кнопку "Назада" в коментариях приложение падает

commentsScreenContent = {
                CommentsScreen(
                    onBackPressed = { commentsToPost.value = null }, <-- падает здесь
                    feedPost = commentsToPost.value!!
                )
            },
Дело в том, что функция зависит от стейта commentsToPost, по бэку мы его меняем на null, происходит рекомпозиция CommentsScreen, а там стоит !!
Поменяем эту реализацию на:

 commentsScreenContent = {
                CommentsScreen(
                    onBackPressed = {
                        navigationState.navHostController.popBackStack() <-- удаляем верхний экран их стека
                                    },
                    feedPost = commentsToPost.value!!
                )
            },
4) Находясь на главном экране нажимаем комментарии. Далее снова нижнее меню "Главная" и переходим на FeedPosts.Желательно, чтобы при нажатии на выбранный пункт, ничего не менялось

Здесь всё просто

NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if(!selected) { <--- добавляем проверку
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
						
5) Если мы находимся на экране комментариев, затем переходим на другую вкладку, возвращаемся на "Главную" , то переходим к списку постов, т.е. стейт сбрасывается

Проблема здесь

class NavigationState(val navHostController: NavHostController) {

    fun navigateTo(route : String){
        navHostController.navigate(route){
            popUpTo(navHostController.graph.findStartDestination().id){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
Если мы перемещаемся по табам, то у нас выкидываются из бэкстека все экраны до главного
Но мы не хотели бы,чтобы экран комментариев из графа выбраслывался. Сделаем для комментариев отдельный метод


fun navigateToComment(){
        navHostController.navigate(Screen.Comments.route)
    }
	
#5.12 Передача параметров в JetpackNavigation. Часть 1. Передача Id

Сейчас feedPost в comment передаётся через mutableStateOf. Переделаем это
 Передадим FeedPost в navigateToComment
 Сегодня научимся передавать только id этого объекта
 
 
 fun navigateToComment(feedPost : FeedPost){
        navHostController.navigate(Screen.Comments.route)
    }
	
И commentScreenContent тоже пусть принимает FeedPost

@Composable
fun AppNavGraph(navHostController: NavHostController,
                newsFeedScreenContent : @Composable () -> Unit,
                favoriteScreenContent : @Composable () -> Unit,
                profileScreenContent : @Composable ()-> Unit,
                commentsScreenContent : @Composable (FeedPost) -> Unit <-----------------
){
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
        ){


       homeScreenNavGraph(
           newsFeedScreenContent = newsFeedScreenContent,
           commentsScreenContent = commentsScreenContent) <-------------

        composable(Screen.Favorite.route){
            favoriteScreenContent()
        }

        composable(Screen.Profile.route){
            profileScreenContent()
        }
    }

}

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit <-------------------
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(Screen.Comments.route) {
            commentsScreenContent(FeedPost()) <-------------------- пока временно просто создаём объект
        }
    }
}

Передача параметра через косую черту

fun navigateToComment(feedPost : FeedPost){
        navHostController.navigate(Screen.Comments.route + "/" + feedPost.id) // comments/15/home_of_comunity  <---------------
    }
	
При этом формировать route здесь неудобно. Сформируем в классе Comments

sealed class Screen(val route: String) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVORITE)
    object Profile : Screen(ROUTE_PROFILE)

    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS){
        
        fun getRouteWithArgs(feedPost : FeedPost) : String { <-------------
            return "$ROUTE_COMMENTS/${feedPost.id}"
        }
    }
	.........................
}

Далее, в графе прописан просто route "comments", а нужно route с параметрами comments/{feed_post_id}

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(Screen.Comments.route) {// comments/{feed_post_id} <-----------
            commentsScreenContent(FeedPost())
        }
    }
}

Пропишем его сразу в пути
Screen{
..............
	object Comments : Screen(ROUTE_COMMENTS){
        
        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost : FeedPost) : String {
            
            return "$ROUTE_FOR_ARGS/${feedPost.id}"
        }
    }
	......
	const val ROUTE_COMMENTS = "comments/{feed_post_id}"
	......
}

Получение аргументов

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(Screen.Comments.route) {
            val feedPostId = it.arguments?.getInt("feed_post_id") ?: 0 <---------------
            commentsScreenContent(FeedPost(id = feedPostId))<--------------
        }
    }
}

Проверим - экран комментраиев открывается ок, но id всегда 0. Почему так происходит посмотрим в следующем уроке

Вынесем название аргумента в отдельную переменную

sealed class Screen(val route: String) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVORITE)
    object Profile : Screen(ROUTE_PROFILE)

    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost : FeedPost) : String {

            return "$ROUTE_FOR_ARGS/${feedPost.id}"
        }
    }


    companion object {
        const val KEY_FEED_POST_ID = "feed_post_id" <-----------

        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST_ID}" <--------------
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"

    }
}


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(Screen.Comments.route) {// comments/{feed_post_id}
            val feedPostId = it.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0 <--------------
            commentsScreenContent(FeedPost(id = feedPostId))
        }
    }
}

#5.13 Передача параметров. Часть 2. Navigation Types

Разберёмся почему всегда postId = 0

При дебаге выявили, что проблема здесь:

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(Screen.Comments.route) {// comments/{feed_post_id}
		// в аргуменах значение приходит корректное, но getInt возвращает 0
            val feedPostId = it.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0 <--------------
            commentsScreenContent(FeedPost(id = feedPostId))
        }
    }
}

По умолчанию все параметры парсятся как строки
Если переделать так,то всё будет работать. Получаем парамтер как строку

 composable(Screen.Comments.route) { entry -> // comments/{feed_post_id}
            Log.d("111", "entry.arguments = ${entry.arguments}")
            val feedPostId = entry.arguments?.getString(Screen.KEY_FEED_POST_ID) ?: "0" <-------------------
            Log.d("111", "feedPostId = $feedPostId")
            commentsScreenContent(FeedPost(id = feedPostId.toInt())) <-----------------------
        }
		
Но это не самое лучшее решение

 composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST_ID){ <----ключ
                    type = NavType.IntType <--- тип
                }
            )
        ) { entry ->             
            val feedPostId = entry.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0            
            commentsScreenContent(FeedPost(id = feedPostId))
        }
	При передаче строк, они не должны содержать спецсимволы, напрмер косая черта /
		
#5.14 Передача параметров. Часть 3. Parcelable, Json, Custom Nav Type

Передавать большие данные не рекомендуется. Лучше передать id объекат, потом загрузить его из репозитория. Но иногда необходимо, поэтому надо знать.
Предположим текст содержит спецсимволы. Их нужно экранировать.

Передадим такой контент

class NewsFeedViewModel : ViewModel() {


    private val initList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(
                FeedPost(
                    id = it,
                    contentText = "Con/tent $it") <------------- контент со спецсимволами
            )
        }
    }


  composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST_ID){
                    type = NavType.IntType
                },
                navArgument(name = Screen.KEY_FEED_POST_CONTENT_TEXT){ <------ передача строки
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val feedPostId = entry.arguments?.getInt(Screen.KEY_FEED_POST_ID) ?: 0
            val content = entry.arguments?.getString(Screen.KEY_FEED_POST_CONTENT_TEXT) ?: ""
            commentsScreenContent(FeedPost(id = feedPostId, contentText = content))
        }

sealed class Screen(val route: String) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVORITE)
    object Profile : Screen(ROUTE_PROFILE)

    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost : FeedPost) : String {

            return "$ROUTE_FOR_ARGS/${feedPost.id}/${feedPost.contentText}" <------------------------ "$ROUTE_FOR_ARGS/${feedPost.id}/${Uri.encode(feedPost.contentText)}"
        }
    }


    companion object {
        const val KEY_FEED_POST_ID = "feedPostId"
        const val KEY_FEED_POST_CONTENT_TEXT = "feedPostContentText" <--------------------

        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST_ID}/{$KEY_FEED_POST_CONTENT_TEXT}" <--------------------
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"

    }
}

Строки передаём используя {Uri.encode(feedPost.contentText) <---- вызывать всегда при передаче строк

Неного рефакторинга

fun String.encode() : String {
    return Uri.encode(this)
}

И...

fun getRouteWithArgs(feedPost : FeedPost) : String {

            return "$ROUTE_FOR_ARGS/${feedPost.id}/${feedPost.contentText.encode()}"
        }
		
Теперь посмотрим как передавать объекты - через представление их в виде строки - формат Json
Добавим зависимость

implementation("com.google.code.gson:gson:2.10")

Передача парамтера:

sealed class Screen(val route: String) {

    object NewsFeed : Screen(ROUTE_NEWS_FEED)
    object Favorite : Screen(ROUTE_FAVORITE)
    object Profile : Screen(ROUTE_PROFILE)

    object Home : Screen(ROUTE_HOME)
    object Comments : Screen(ROUTE_COMMENTS){

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost : FeedPost) : String {

            val feedPostJson = Gson().toJson(feedPost) <---------------------
            return "$ROUTE_FOR_ARGS/${feedPostJson.encode()}" <----------------
        }
    }


    companion object {
        const val KEY_FEED_POST = "feedPost" <------------------

        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}" <---------------
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVORITE = "favorite"
        const val ROUTE_PROFILE = "profile"

    }
}

Получение параметра:

fun NavGraphBuilder.homeScreenNavGraph(
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit
) {
    navigation(
        startDestination = Screen.NewsFeed.route,
        route = Screen.Home.route
    ) {
        composable(Screen.NewsFeed.route) {
            newsFeedScreenContent()
        }
        composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val feedPostJson = entry.arguments?.getString(Screen.KEY_FEED_POST) ?: ""
            val feedPost = Gson().fromJson<FeedPost>(feedPostJson, FeedPost::class.java)
            commentsScreenContent(feedPost)
        }
    }
}

Есть другой способ: Создать свой тип
Подключаем Kotlin Parcelize
kotlinParcelize = "1.4.20"
kotlinParcelize = {id = "org.jetbrains.kotlin.android", version.ref = "kotlinParcelize"}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.kotlinParcelize ) apply false <---------------
}

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinParcelize ) <----------------
}

По аналогии с StringType создаём свой тип:

@Parcelize
data class FeedPost(
    val id: Int = 0,
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
) : Parcelable{

    companion object{

        val NavigationType: NavType<FeedPost> = object : NavType<FeedPost>(false){
            override fun get(
                bundle: Bundle,
                key: String
            ): FeedPost? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): FeedPost {
                return Gson().fromJson<FeedPost>(value, FeedPost::class.java)
            }

            override fun put(
                bundle: Bundle,
                key: String,
                value: FeedPost
            ) {
                bundle.putParcelable(key,value)
            }

        }
    }
}


composable(
            route = Screen.Comments.route,
            arguments = listOf(
                navArgument(Screen.KEY_FEED_POST){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val feedPostJson = entry.arguments?.getString(Screen.KEY_FEED_POST) ?: "" 		|<---------------
            val feedPost = Gson().fromJson<FeedPost>(feedPostJson, FeedPost::class.java) 	|<--------------- 
			
			----> val feedPost = entry.arguments?.getParcelable<FeedPost>(Screen.KEY_FEED_POST) 
            ---->    ?: throw RuntimeException("Args is null")
            commentsScreenContent(feedPost)
        }
		
#7.1 Авторизация через VK SDK		
		
> Task :app:signingReport
Variant: debug
Config: debug
Store: C:\Users\melol\.android\debug.keystore
Alias: AndroidDebugKey
MD5: BD:16:F8:A5:28:0B:BB:55:99:5E:2B:EA:4B:02:81:C7
SHA1: B4:D2:51:05:69:4C:0A:6E:61:EE:9D:C3:4F:1D:E3:6C:A6:45:C3:66
SHA-256: EC:22:46:43:7A:1B:2A:87:74:7B:01:DD:22:1D:B0:19:B9:00:52:47:A2:B4:AE:C0:B9:D6:0F:EA:C5:C0:83:EA
Valid until: понедельник, 26 февраля 2052 г.
----------
Variant: release
Config: null
Store: null
Alias: null
----------
Variant: debugAndroidTest
Config: debug
Store: C:\Users\melol\.android\debug.keystore
Alias: AndroidDebugKey
MD5: BD:16:F8:A5:28:0B:BB:55:99:5E:2B:EA:4B:02:81:C7
SHA1: B4:D2:51:05:69:4C:0A:6E:61:EE:9D:C3:4F:1D:E3:6C:A6:45:C3:66
SHA-256: EC:22:46:43:7A:1B:2A:87:74:7B:01:DD:22:1D:B0:19:B9:00:52:47:A2:B4:AE:C0:B9:D6:0F:EA:C5:C0:83:EA
Valid until: понедельник, 26 февраля 2052 г.


54290700 - ID


Авторизация по документации

				val authLauncher = VK.login(this){
                    when(it){
                        is VKAuthenticationResult.Failed -> {
                            Log.d("MainActivity","Failed")
                        }
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity","Success")
                        }
                    }

                }
                authLauncher.launch(arrayListOf(VKScope.WALL, VKScope.PHOTOS))
				
Но в Compose OnActivityResult получается через контракт следующим образом

rememberLauncherForActivityResult() { }

Заходя в логин находим функцию для получения контракта - getVKAuthActivityResultContract()

Получается следующее

 val authLauncher = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) {
                    when(it){
                        is VKAuthenticationResult.Failed -> {
                            Log.d("MainActivity","Failed")
                        }
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity","Success")
                        }
                    }
                }
				
authLauncher.launch(listOf(VKScope.WALL))

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {

               val authLauncher = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) {
                    when(it){
                        is VKAuthenticationResult.Failed -> {
                            Log.d("MainActivity","Failed")
                        }
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity","Success")
                        }
                    }
                }

                authLauncher.launch(listOf(VKScope.WALL))
                MainScreen()

            }
        }
    }
	....
	
	Запускаем так и приложение крашится

#7.2 SideEffect и LaunchedEffect


fun sum(a: Int, b: Int) : Int{ <---- чистая функция
    return a + b;
}

Немного перепишем

var result = 0
fun sum(a: Int, b: Int) : Int{
    result =  a + b
    return result
}
Так ф-ция перестает быть чистой, имеет сайд-эффекты, потому что кроме прямого назначения делает что-то за пределами скоупа своей ответственности


Другой пример

@Composable
fun SideEffectTest(number : MyNumber){
    LazyColumn {
        repeat(5){
            item { 
                Text(text = "Number: ${number.a}")
            }
        }
    }
    
}

data class MyNumber(var a: Int)

Сейчас SideEffectTest чистая функция, её зона ответственности выводить таблицу
Изменим:

@Composable
fun SideEffectTest(number : MyNumber){
    Column {
        LazyColumn {
            repeat(5){
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
        number.a = 5 <--- меняем поле, сайд эффект

        LazyColumn {
            repeat(5){
                item {
                    Text(text = "Number: ${number.a}")
                }
            }
        }
    }
   
}

Выполнение не будет последовательным
Функции в Jetpack Compose могут быть вызваны в любом порядке и нельзя расчитывать на порядок.
SideEffectTest - должна заниматься только отрисовкой и реагировать на действия пользователя, а мы добавляем изменение объекта во время рекомпозиции
Так делать никогда не стоит!
Вернёмся к нашей проблеме:

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {

               val authLauncher = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) {
                    when(it){
                        is VKAuthenticationResult.Failed -> {
                            Log.d("MainActivity","Failed")
                        }
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity","Success")
                        }
                    }
                }

                authLauncher.launch(listOf(VKScope.WALL)) <--- сайд эффект , меняем стейт во время рекомпозиции, нужно перевести на клик, например
                MainScreen()

            }
        }
    }
	....
	
	Бывает без сайд эффектов не обойтись - нужно выполнить действие в процессе композиции
	В таком случае можно воспользоваться функциями их Effect API
	Например, запуск при открытии приложения.
	Одна из них SideEffect{}
	
	class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {

               val authLauncher = rememberLauncherForActivityResult(VK.getVKAuthActivityResultContract()) {
                    when(it){
                        is VKAuthenticationResult.Failed -> {
                            Log.d("MainActivity","Failed")
                        }
                        is VKAuthenticationResult.Success -> {
                            Log.d("MainActivity","Success")
                        }
                    }
                }
                SideEffect {
                    authLauncher.launch(listOf(VKScope.WALL)) <--- код внутри будет вызван при успешной рекомпозиции всей Composable-функции!
                }
                
                MainScreen()

            }
        }
    }
	
	Есть ещё одна LaunchedEffect(Key1 = ...){
			// что выполнять
	}
	
	Пример
	
	class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {

                val someState = remember {
                    mutableStateOf(true)
                }
                Log.d("MainActivity", "Recomposition ${someState.value}")

                SideEffect {
                    Log.d("MainActivity", "SideEffect") <---- Вызывается при каждой успешной рекомпозиции И здесь при каждом изменении state
														<----!!!! Нельзя вызывать suspend функции
                }

                LaunchedEffect(key1 = true) {
                    Log.d("MainActivity", "LaunchedEffect") <---- Вызывается только один раз и при изменении ключа key1/ Поэтому если в качестве ключа указать state,
															<---- то код будет вызываться при каждой смене state
															<----!!!! Находимся внутри scope и можем вызывать suspend функции
											
					
                }

                // MainScreen()
                Button(
                    onClick = {
                        someState.value = !someState.value
                    }) {
                    Text(text = "Change state")
                }

            }
        }
    }
	
	Функции могут выполняться в любой последовательности и Side эффектов лучше избегать.
	
	#7.3 Login Screen
	
Добавляем:

@Composable
fun LoginScreen(onLoginClickListener: () -> Unit){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Column(modifier = Modifier.wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(modifier = Modifier.size(100.dp),
                painter = painterResource(R.drawable.vk_logo),
                contentDescription = null)
            Spacer(modifier = Modifier.height(100.dp))
            Button(onClick = { onLoginClickListener() },
                colors = ButtonDefaults.buttonColors(containerColor = DarkBlue, contentColor = colorResource(R.color.white))) {
                    Text(text = stringResource(R.string.button_login))
            }
        }
    }
}


sealed class AuthState {
    object Initial : AuthState()
    object Authorized : AuthState()
    object NotAuthorized : AuthState()
}

Т.к. sdk поменяелось делаю так

class MainViewModel(application: Application) : AndroidViewModel(application = application) {

    private val _authSate  = MutableLiveData<AuthState>(AuthState.Initial)

    val authState : LiveData<AuthState> = _authSate

    init {
        _authSate.value = if(VKID.instance.accessToken != null) AuthState.Authorized else AuthState.NotAuthorized
    }

    fun login(){
        Log.d("MainViewModel", "login")
        viewModelScope.launch {
            val vkAuthCallback = object : VKIDAuthCallback {
                override fun onAuth(accessToken: AccessToken) {
                    val token = accessToken.token
                    Log.d("MainViewModel", "Success token = $token")
                    _authSate.postValue(AuthState.Authorized)
                }

                override fun onFail(fail: VKIDAuthFail) {

                    Log.d("MainViewModel", "VKIDAuthFail = $fail")
                    when (fail) {
                        is VKIDAuthFail.Canceled -> { /*...*/

                        }

                        else -> {
                            //...
                        }
                    }
                }
            }

            VKID.instance.authorize(vkAuthCallback)
        }
    }

}


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel : MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState()
                when(authState.value){
                    AuthState.Authorized -> MainScreen()
                    AuthState.NotAuthorized -> LoginScreen { viewModel.login() }
                    else -> {}
                }
            }
        }
    }
	
	
#7.4 Refactoring и хранение токена

#7.5 Загружаем данные

Основные моменты:

object ApiFactory {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.vk.ru/method/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}

interface ApiService {

    @GET("newsfeed.get?filters=post&v=5.199")
    suspend fun loadNewsfeed(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}


class NewsFeedViewModel : ViewModel() {

    private val mapper = NewsFeedMapper()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val token = VKID.instance.accessToken?.token ?: return@launch
            val response = ApiFactory.apiService.loadNewsfeed(token)
            val posts = mapper.mapResponseToPosts(response)
            _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
        }
    }
	.......
	
	
AsyncImage(
            model = feedPost.communityImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(shape = CircleShape),
            contentDescription = null
        )
		
		
#7.6 Дорабатываем UI

1) Формат даты в маппере
2) Формат статистики в view
3)Иконка лайка красная
Добавляем иконку ic_like_set

Добавим

data class FeedPost(
    val id: String,
    ..............
    val isFavorite : Boolean = Random.nextBoolean() <------------
	)
	
IconWithText(
                iconResId = if(isFavorite) R.drawable.ic_like_set else R.drawable.ic_like,
                formatStatisticCount(likesItem.count)
            )
Но нужно ещё цвет иконки поправить = тинт


#7.7 Реализация добавления/удаления лайков

Для начала всю работу с данными вынесем в репозиторий

class NewsFeedRepository {

    private val accessToken = VKID.instance.accessToken

    private val apiService = ApiFactory.apiService

    private val mapper = NewsFeedMapper()


    suspend fun loadData(): List<FeedPost> {
        val response = apiService.loadNewsfeed(getAccessToken())
        return mapper.mapResponseToPosts(response)
    }

    private fun getAccessToken(): String {
        return accessToken?.token ?: throw IllegalStateException("Token is null")
    }
}



interface ApiService {

    @GET("newsfeed.get?filters=post&v=5.199")
    suspend fun loadNewsfeed(
        @Query("access_token") token: String
    ): NewsFeedResponseDto


    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    )
}

class NewsFeedRepository {

    private val accessToken = VKID.instance.accessToken

    private val apiService = ApiFactory.apiService

    private val mapper = NewsFeedMapper()


    suspend fun loadData(): List<FeedPost> {
        val response = apiService.loadNewsfeed(getAccessToken())
        return mapper.mapResponseToPosts(response)
    }

    private fun getAccessToken(): String {
        return accessToken?.token ?: throw IllegalStateException("Token is null")
    }

    suspend fun addLike(feedPost : FeedPost){
        apiService.addLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id

        )
    }
}


class NewsFeedViewModel : ViewModel() {

    private val repository = NewsFeedRepository()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val posts = repository.loadData()
            _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
        }
    }

    fun changeLikeStatus(feedPost: FeedPost){
        viewModelScope.launch {
            repository.addLike(feedPost = feedPost)
        }
    }
	.............
	}
	
И вызываем по клику на лайка

 onLikeClickListener = { statisticItem ->
                            //viewModel.updateStatistics(feedPost, statisticItem)
                            viewModel.changeLikeStatus(feedPost = feedPost) <----------------------------
                        }
						
Ответ по API - {"response" " {"likes" : 1729 }}

Добавим возвращаемое значение

 @GET("likes.add?v=5.199&type=post") 
    suspend fun addLike(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
    ) : LikesCountResponseDto <---------------------- 
	
	
	В репозитории пока получим его
	
	suspend fun addLike(feedPost : FeedPost){
        val response =  apiService.addLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id

        )
    }
	
	Теперь нужно обновить пост и кол-во лайков в нём.И пометить иконку красным.
	Теоретически можно снова загрузить список постов с сервера, но это плохое решение.
	Это излишне и доп.время.
	Пойдём по другому пути.
	
	Будем сохранять списко локально в репозитории
	
	private val feedPosts = mutableListOf<FeedPost>() <------------


    suspend fun loadData(): List<FeedPost> {
        val response = apiService.loadNewsfeed(getAccessToken())
        val posts = mapper.mapResponseToPosts(response)
        feedPosts.addAll(posts) <---------------
        return posts
    }
	
	suspend fun addLike(feedPost : FeedPost){
        val response =  apiService.addLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id

        )
        val newLikesCount = response.likes.count <------- новое кол-во лайков
        val newStatistics = feedPost.statistics.toMutableList().apply { <--- создаём копию статистики и меняем элемент LIKES
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        
        val newPost = feedPost.copy(statistics = newStatistics, isFavorite = true) <--- Создаём новый feedPost
        val postIndex = feedPosts.indexOf(feedPost)
        feedPosts[postIndex] = newPost <---- заменяем его в списке
    }
	
	
	private fun loadData() {
        viewModelScope.launch {
            val posts = repository.loadData()
            _screenState.postValue(NewsFeedScreenState.Posts(posts = posts)) <---- здесь что загрузили
        }
    }

    fun changeLikeStatus(feedPost: FeedPost){
        viewModelScope.launch {
            repository.addLike(feedPost = feedPost)
            _screenState.postValue(NewsFeedScreenState.Posts(posts = repository.feedPosts)) <---- здесь коллекция из репозитория
        }
    }
	
	private val feedPosts = mutableListOf<FeedPost>() <------------ не можем сделать public
	
	Сделаем так
	
	private val _feedPosts = mutableListOf<FeedPost>() <---- внутри изменяемая коллекция
    
    val feedPosts : List<FeedPost> <---- возвращаем неизменяемую копию
        get() = _feedPosts.toList()
		
		
		
#7.8 Подгрузка данных во время скролла

Когда доскролим до конца появляется прогресс бар и появляются следующие посты

В запросе - предыдущее значение next_from используется для поля start_from. Реализуем это в data слое:


  @GET("newsfeed.get?filters=post&v=5.199")
    suspend fun loadNewsfeed(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String,
        
    ): NewsFeedResponseDto


Добавим:

data class NewsFeedContentDto(
    @SerializedName("items") val posts: List<PostDto>,
    @SerializedName("groups") val groups: List<GroupDto>,
    @SerializedName("next_from") val nextFrom: String? <------------------ 
)


class NewsFeedRepository {

    ......................
    
    private var nextFrom : String? = null;

    .............................


    suspend fun loadData(): List<FeedPost> {
        val startFrom = nextFrom <------------- обязательно
        val response = if(startFrom == null) {
            apiService.loadNewsfeed(getAccessToken())
        }else{
            apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom <---------------- 
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return posts
    }
	
	Есть нюанс - поле nextFrom может быть null в двух случаях - при первой загрузке и при ококнчании рекомендаций.
	
	 suspend fun loadData(): List<FeedPost> {
        val startFrom = nextFrom
        if(startFrom == null && feedPosts.isNotEmpty()) return feedPosts <---- добавляем условие
        val response = if(startFrom == null) {
            apiService.loadNewsfeed(getAccessToken())
        }else{
            apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return feedPosts <----------------
    }


Далее в стейт добавим  val nextDataIsLoading : Boolean

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()
    data class Posts(val posts : List<FeedPost>, val nextDataIsLoading : Boolean = false) : NewsFeedScreenState()
}

Когда проскролим до конца установим посты из репозитория и nextDataIsLoading = true

fun loadNextData() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        viewModelScope.launch {
            val posts = repository.loadData()
            _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
        }
    }
	
	
	или
	
	
	fun loadNextData() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadData()
    }
	
	
	@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun FeedPosts(
    viewModel: NewsFeedViewModel,
    paddingValues: PaddingValues,
    posts: List<FeedPost>,
    onCommentsClickListener: (FeedPost) -> Unit,
    nextDataIsLoading : Boolean <------------------------ add variable
){
    LazyColumn(modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts, key = {it.id }){ feedPost ->
            ... show items here

        }
		//Когда проскролимся до конца списка появится этот элемент. Сначала nextDataIsLoading = false и вызовется loadNextData(), а там nextDataIsLoading присваивается значение true
        item { <--- add item with progress
            if(nextDataIsLoading){
                Box(modifier = Modifier.fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator(color = DarkBlue)
                }
            }else{
                SideEffect {
                    viewModel.loadNextData()
                }
            }
        }
    }
}


#7.9 Добавление состояния загрузки и реализация удаления элементов


@GET("newsfeed.ignoreItem?type=wall&v=5.199")
    suspend fun ignoreItem(
        @Query("access_token") token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") postId: Long
        )
class NewsFeedRepository {
...		
	suspend fun deletePost(feedPost: FeedPost)  {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
    }
....

class NewsFeedViewModel : ViewModel() {
	......
	fun deleteItem(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) {
            return
        }
        viewModelScope.launch {
            repository.deletePost(feedPost = feedPost)
            _screenState.value = NewsFeedScreenState.Posts(repository.feedPosts)
        }
    }
	......
	
!!!!!!!!!Теперь добавим лоадинг при первой загрузке 
	
	sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    object Loading : NewsFeedScreenState() <--------------
    
    data class Posts(val posts : List<FeedPost>,
                     val nextDataIsLoading : Boolean = false) : NewsFeedScreenState()
}

class NewsFeedViewModel : ViewModel() {
	..............
	init {
        _screenState.value = NewsFeedScreenState.Loading
        loadData()
    }
	..............
	}
	

fun NewsFeedScreen(
    paddingValues : PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
){

    val viewModel : NewsFeedViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    when(val currentState = screenState.value){
        is NewsFeedScreenState.Posts -> {
            FeedPosts(posts = currentState.posts, viewModel = viewModel,
                paddingValues = paddingValues ,
                onCommentsClickListener = onCommentsClickListener,
                nextDataIsLoading = currentState.nextDataIsLoading)
        }

        NewsFeedScreenState.Initial -> {

        }

        NewsFeedScreenState.Loading -> { <--------------------------------
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}


Поправим клики на статистику

Удаляем из ViewModel updateStatistics
Переходим во view


PostCard(
                        modifier = Modifier,
                        feedPost = feedPost,
                        onViewsClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost, statisticItem) <---------------
                        },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateStatistics(feedPost, statisticItem) <---------------
                        },
                        onCommentsClickListener = { statisticItem ->
                            onCommentsClickListener(feedPost)
                        },
                        onLikeClickListener = { statisticItem ->
                            //viewModel.updateStatistics(feedPost, statisticItem)
                            viewModel.changeLikeStatus(feedPost = feedPost)
                        }
                    )
					
Просто удалить их не совсем корректно, будет отображаться клик, как будто есть какое-то действие, поэтому убираем клики из PostCard

Но private fun IconWithText(
    iconResId: Int, text: String,
    onItemClickListener: () -> Unit <----------- всегда принимает клик
) 

Сделаем его необязательным и по умолчанию передадим null


@Composable
private fun IconWithText(
    iconResId: Int, text: String,
    onItemClickListener: (() -> Unit)? = null <----------------
) {
    
    val modifier = if(onItemClickListener != null){ <--------------
         Modifier.clickable {
            onItemClickListener()
        }
    }else{
        Modifier
    }
    Row(
        modifier = modifier, <------------------
        verticalAlignment = Alignment.CenterVertically
    )
	
	Там где не надо убираем листенеры
	
	
#7.10 Реализация экрана комментариев

#9.1 Practice in VkNewsClient

До этого мы не использовали реактивный подход и если данные в репозитории  менялись, их нужно запрашивать занова

Перепишем метод

class NewsFeedRepository {


    suspend fun loadData(): List<FeedPost> {
        val startFrom = nextFrom
        if (startFrom == null && feedPosts.isNotEmpty()) return feedPosts
        val response = if (startFrom == null) {
            apiService.loadNewsfeed(getAccessToken())
        } else {
            apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        return feedPosts
    }
	
	------------------------------------------------------->>>
	
	 fun loadData(): Flow<List<FeedPost>> = flow{
        val startFrom = nextFrom
        if (startFrom == null && feedPosts.isNotEmpty()) {
            emit(feedPosts)
            return@flow
        }
        val response = if (startFrom == null) {
            apiService.loadNewsfeed(getAccessToken())
        } else {
            apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
        }
        nextFrom = response.newsFeedContent.nextFrom
        val posts = mapper.mapResponseToPosts(response)
        _feedPosts.addAll(posts)
        emit(feedPosts)
    }
	
	
	Меняем загрузку данных из ViewModel
	
	 private fun loadData() {
        viewModelScope.launch {
            val posts = repository.loadData()
            _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
        }
    }
	------------------------------------------------------------->>>>
	
	 private fun loadData() {
        viewModelScope.launch {
            repository.loadData()
                .collect { posts ->
                    _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
                }
        }
    }
	
	  fun loadNextData() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadData()
    }
	
	Но возвращать холодный flow из репозитория не очень хорошая идея. Т.к. при каждой подписке будет создаваться новый поток данных и загрузка будет начинаться занова.
	
	Превратим холодный flow в горячий с помощью stateIn
	class NewsFeedRepository {

    private val coroutineScope  = CoroutineScope(Dispatchers.Default)
	
	
	    fun loadData(): Flow<List<FeedPost>> = flow{
			val startFrom = nextFrom
			if (startFrom == null && feedPosts.isNotEmpty()) {
				emit(feedPosts)
				return@flow
			}
			val response = if (startFrom == null) {
				apiService.loadNewsfeed(getAccessToken())
			} else {
				apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
			}
			nextFrom = response.newsFeedContent.nextFrom
			val posts = mapper.mapResponseToPosts(response)
			_feedPosts.addAll(posts)
			emit(feedPosts)
		}.stateIn( <------------------------------------------------------------------------------------
			scope = coroutineScope,<------------------------------------------------------------------------------------
			started = SharingStarted.Lazily,<------------------------------------------------------------------------------------
			initialValue = feedPosts<------------------------------------------------------------------------------------
        )
		
Но здесь есть ошибка, если посмотреть в лог будет много загрузок и обращений к серверу через API. 
Причина в том, что мы не фильтруем пустое значение списка которое эмиттим при инициализвции.


Добавляем фильтрацию

  private fun loadData() {
        viewModelScope.launch {
            repository.loadData()
                .filter { posts -> posts.isNotEmpty() } <--------------------
                .collect { posts ->
                    _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
                }
        }
    }
	
	Следующая проблема: 
	
	fun loadData(): Flow<List<FeedPost>> = flow{ здесь каждый раз создаётся новый объект flow и НОВЫЙ ПОТОК ДАННЫХ и преобразуется в горячий из-за этого могут возникать различные ошибки.
			........................
			emit(feedPosts)
		}.stateIn( 
			scope = coroutineScope,
			started = SharingStarted.Lazily,
			initialValue = feedPosts
        )
		
	ФИксится так - просто меняем функцию на val переменную
	
	val data: Flow<List<FeedPost>> = flow{
	}.....
	
	
	Но теперь при прокуртке вниз progress не показывается, т.к. flow повторно не запускается 
	
	private fun loadData() {
        viewModelScope.launch {
            repository.data
                .filter { posts -> posts.isNotEmpty() }
                .collect { posts -> <----------------- Здесь мы просто подписываемся на существующий горячий flow
                    _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
                }
        }
    }

    fun loadNextData() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        loadData()
    }
	
	Нужно,чтобы при вызове loadNextData() загружались новые данные
	
	Создадим в репозитории MutableSharedFlow, который будет хранить эвенты
	
	
	class NewsFeedRepository {

    private val coroutineScope  = CoroutineScope(Dispatchers.Default)
    
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1) <----------------------- смотри ниже почему
   
    ............................

    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()


    val data: Flow<List<FeedPost>> = flow{
        nextDataNeededEvents.emit(Unit)<------------------- если оставить replay = 0, то в следующей строке мы это значение не получим, поэтому replay = 1
        nextDataNeededEvents.collect { <--------------------- при получении эвента выполняем все эти действия
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadNewsfeed(getAccessToken())
            } else {
                apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
       
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts
        )
		
		Почему нельзя использовать MutableStateFlow вместо MutableSharedFlow - мы отправляем одно и тоже значение Unit и 
		оно не меняется и в state будет фильтроваться и коллектор не будет отрабатывать
		
		
		Добавим 
		
suspend fun loadNextData(){
        nextDataNeededEvents.emit(Unit)
    }
	
	
class NewsFeedViewModel : ViewModel() {

    private val repository = NewsFeedRepository()

    private val initState = NewsFeedScreenState.Initial
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initState)

    val screenState: LiveData<NewsFeedScreenState> = _screenState

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.data
                .filter { posts -> posts.isNotEmpty() }
                .collect { posts ->
                    _screenState.postValue(NewsFeedScreenState.Posts(posts = posts))
                }
        }
    }

    fun loadNextData() {
        _screenState.value = NewsFeedScreenState.Posts(
            posts = repository.feedPosts,
            nextDataIsLoading = true
        )
        viewModelScope.launch {
            repository.loadNextData() <--------------------------
        }
        
    }
	
	Далее уберём LiveData<NewsFeedScreenState>
	
	
	class NewsFeedViewModel : ViewModel() {

    private val repository = NewsFeedRepository()

    val repositoryDataFlow = repository.data

    val loadingState = MutableSharedFlow<NewsFeedScreenState>()
	
    val screenState = repositoryDataFlow
        .filter { posts -> posts.isNotEmpty() }
        .map {
            NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState
        }
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadingState)


    fun <T> Flow<T>.mergeWith(other : Flow<T>) : Flow<T> {
        return merge(this, other)
    }
    fun loadNextData() {
       
        viewModelScope.launch {
            loadingState.emit(
                NewsFeedScreenState.Posts(
                    posts = repositoryDataFlow.value,
                    nextDataIsLoading = true
                )
            ) 
            repository.loadNextData()
        }

    }
	.............
	
	При такой реализации вот тут будут ошибки
	
	 fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch {
            if (feedPost.isLiked) {
                repository.deleteLike(feedPost = feedPost)
            } else {
                repository.addLike(feedPost = feedPost)
            }
            _screenState.postValue(NewsFeedScreenState.Posts(posts = repository.feedPosts)) <<--------------------
        }
    }


    fun deleteItem(feedPost: FeedPost) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) {
            return
        }
        viewModelScope.launch {
            repository.deletePost(feedPost = feedPost)
            _screenState.value = NewsFeedScreenState.Posts(repository.feedPosts) <<--------------------
        }
    }
	
	Реализуем обновление данных в репозитории
	
	Добавим
	
	private val refreshDataEvents = MutableSharedFlow<List<FeedPost>>()
		
		На примере deletePost:
		
	suspend fun deletePost(feedPost: FeedPost)  {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshDataEvents.emit(feedPosts) <----

    }
	
И примёрджим к основному стейту


val data: StateFlow<List<FeedPost>> = flow{
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadNewsfeed(getAccessToken())
            } else {
                apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }

    }.mergeWith(refreshDataEvents) <<-------------------------------------------------- вот тут
        .stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts
        )
		
		Немного отрефакторим
		
		
Осталась одна доработка:

@Composable
fun NewsFeedScreen(
    paddingValues : PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
){

    val viewModel : NewsFeedViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial) 
	------------------------------------------------------------------------------------->>> меняем на 
	 val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)
	 
	 
#9.2 Exception handling in VkNewsClient

Отключим интернет и поработаем с ошибками
При появлении интернета данные должны подгрузиться. Поставим retry

 val loadedListFlow = flow{
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadNewsfeed(getAccessToken())
            } else {
                apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }
    .retry { true } <---- true повторение запроса, не зависимо от ошибки

	
	----
	
	.retry {
        delay(RETRY_TIMEOUT_MILLIS)  <---- true повторение запроса c задержкой , RETRY_TIMEOUT_MILLIS - наша переменная
        true }
		
	----

	.retry (2){ <---- ограничить кол-во попыток: одна неудачная загрузка + 2 попытки
        delay(RETRY_TIMEOUT_MILLIS)  
        true }	
		
	Далее добавляем catch - который обрабаотывает ошибкт
	
	Блок catch и retry добавляют только к холодным flow. Добавлять его в горячий flow бессмыссленно, т.к. он никогда не завершается.
	
	
	Поэтому можно в репозтории т=использовать стейт Error и возвращать его во ViewModel
	
	Тепепрь посмотрим на функцию 
	
	  suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshDataEvents.emit(feedPosts)

    }
	1) вариант обработки ошибок try..catch 
	
	 suspend fun deletePost(feedPost: FeedPost) {
	 try{
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshDataEvents.emit(feedPosts)

	}catch (ex : Exception){
	
	....................... обработка здесь 
    }
	
	2) вариани через ExceptionHandler
	
	
	class NewsFeedViewModel : ViewModel() {

    
    val exceptionHandler = CoroutineExceptionHandler { _, _, ->
        ///log
        
    }
	
	....................
	
	fun changeLikeStatus(feedPost: FeedPost) {
        viewModelScope.launch(exceptionHandler) { <---------------------- передаём в корутину
            if (feedPost.isLiked) {
                repository.deleteLike(feedPost = feedPost)
            } else {
                repository.addLike(feedPost = feedPost)
            }
        }
    }


    fun deleteItem(feedPost: FeedPost) {

        viewModelScope.launch(exceptionHandler) {  <---------------------- передаём в корутину
            repository.deletePost(feedPost = feedPost)
        }
    }

}

Поменяем LiveData на экране комментариев

В репозитории:

 suspend fun getComments(feedPost: FeedPost): List<PostComment> {
        val comments = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.mapResponseToComments(comments)
    }
	
	----------------------------->>>>>>>>
	
	
	fun getCommentsFlow(feedPost: FeedPost): Flow<List<PostComment>> = flow {
            val comments = apiService.getComments(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
            emit(mapper.mapResponseToComments(comments))
        }.retry {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }
        .stateIn( <-------------- можно убрать. Т.к. загружаем комментарии один раз
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )
		
#9.3 Coroutine Flow in MainViewModel

Уберём LiveData в MainViewModel

Добавляем в репозиторий работу с AuthState

 val checkAuthStateFlow = MutableSharedFlow<Unit>( replay = 1)


    val authStateFlow = flow {
        checkAuthStateFlow.emit(Unit)
        checkAuthStateFlow.collect {
            val authState = if(VKID.instance.accessToken != null) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }

    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts
    )

    suspend fun checkAuthState(){
        checkAuthStateFlow.emit(Unit)
    }
	
	
class MainViewModel(application: Application) : AndroidViewModel(application = application) {

    private val repository = NewsFeedRepository()

    val authState = repository.authStateFlow
    
   fun login(){
        Log.d("MainViewModel", "login")
        viewModelScope.launch {
            val vkAuthCallback = object : VKIDAuthCallback {
                override fun onAuth(accessToken: AccessToken) {
                    val token = accessToken.token
                    Log.d("MainViewModel", "Success token = $token")
                    viewModelScope.launch {
                        repository.checkAuthState()
                    }
                   
                }

                override fun onFail(fail: VKIDAuthFail) {

                    Log.d("MainViewModel", "VKIDAuthFail = $fail")
                    viewModelScope.launch {
                        repository.checkAuthState()
                    }
                    when (fail) {
                        is VKIDAuthFail.Canceled -> { /*...*/

                        }

                        else -> {
                            //...
                        }
                    }
                }
            }

            val initializer = VKIDAuthParams.Builder().apply {
                scopes = setOf("wall","friends")

            }.build()

            VKID.instance.authorize(callback =  vkAuthCallback,
                params = initializer)
        }
    }

}

#9.4 Refactoring. Clean Architecture

В domain создадим пакет entity и перенесём туда все модели

Там же создадим пакет usecases
И repository. Добавим интерфейс репозитория

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getDataListFlow(): StateFlow<List<FeedPost>>

    fun getCommentsFlow(feedPost: FeedPost): Flow<List<PostComment>>

    suspend fun checkAuthState()

    suspend fun loadNextData()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)


}

Добиваемся того,чтобы domain слой не от чего не зависел

Теперь добавим юзкейсы

class GetFeedPostsListUseCase(val repository: NewsFeedRepository) {
    operator fun invoke() : StateFlow<List<FeedPost>> {
        return repository.getFeedPostsListFlow()
    }
}

class GetCommentsUseCase(val repository: NewsFeedRepository) {
    operator fun invoke(feedPost: FeedPost): Flow<List<PostComment>> {
        return repository.getCommentsFlow(feedPost = feedPost)
    }
}


class GetAuthStateFlowUseCase(val repository: NewsFeedRepository) {
    operator fun invoke(): Flow<AuthState> {
        return repository.getAuthStateFlow()
    }
}


class LoadNextDataUseCase(val repository: NewsFeedRepository) {
    suspend operator fun invoke() {
        repository.loadNextData()
    }
}

class CheckAuthStateUseCase(val repository: NewsFeedRepository) {
    suspend operator fun invoke() {
        return repository.checkAuthState()
    }
}

class ChangeLikeStatusUseCase(val repository: NewsFeedRepository) {
    suspend operator fun invoke(feedPost: FeedPost) {
        return repository.changeLikeStatus(feedPost = feedPost)
    }
}

class DeletePostUseCase(val repository: NewsFeedRepository) {
    suspend operator fun invoke(feedPost: FeedPost) {
        return repository.deletePost(feedPost = feedPost)
    }
}

Domain слой готов

Изменяем реализацию репозитория. Всё что было паблк делаем приватным. Реализуем соотв-е функции, некоторым просто добавляем override

class NewsFeedRepositoryImpl : NewsFeedRepository{

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)

    private val refreshDataEvents = MutableSharedFlow<List<FeedPost>>()
    private val accessToken = VKID.instance.accessToken

    private val apiService = ApiFactory.apiService

    private val mapper = NewsFeedMapper()

    private var nextFrom: String? = null;

    private val _feedPosts = mutableListOf<FeedPost>()

    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private val checkAuthStateFlow = MutableSharedFlow<Unit>( replay = 1)


    private val authStateFlow = flow {
        checkAuthStateFlow.emit(Unit)
        checkAuthStateFlow.collect {
            val authState = if(VKID.instance.accessToken != null) AuthState.Authorized else AuthState.NotAuthorized
            emit(authState)
        }

    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = feedPosts
    )

    override suspend fun checkAuthState(){
        checkAuthStateFlow.emit(Unit)
    }
    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }
            val response = if (startFrom == null) {
                apiService.loadNewsfeed(getAccessToken())
            } else {
                apiService.loadNewsfeed(getAccessToken(), startFrom = startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPosts(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }
        .retry(2) {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }.catch {

        }


    private val data: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshDataEvents)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override suspend fun loadNextData() {
        nextDataNeededEvents.emit(Unit)
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignoreItem(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshDataEvents.emit(feedPosts)

    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        if(feedPost.isLiked){
            deleteLike(feedPost = feedPost)
        }else{
            addLike(feedPost = feedPost)
        }
    }

    override fun getAuthStateFlow(): StateFlow<AuthState>  = authStateFlow as StateFlow<AuthState>

    override fun getFeedPostsListFlow(): StateFlow<List<FeedPost>>  = data

    override fun getCommentsFlow(feedPost: FeedPost): Flow<List<PostComment>> = flow {
            val comments = apiService.getComments(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                postId = feedPost.id
            )
            emit(mapper.mapResponseToComments(comments))
        }.retry {
            delay(RETRY_TIMEOUT_MILLIS)
            true
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = listOf()
        )

    private fun getAccessToken(): String {
        return accessToken?.token ?: throw IllegalStateException("Token is null")
    }

    suspend fun addLike(feedPost: FeedPost) {
        val response = apiService.addLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id

        )
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }

        val newPost = feedPost.copy(statistics = newStatistics, isLiked = true)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshDataEvents.emit(feedPosts)
    }

    suspend fun deleteLike(feedPost: FeedPost) {
        val response = apiService.deleteLike(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id

        )
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }

        val newPost = feedPost.copy(statistics = newStatistics, isLiked = false)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshDataEvents.emit(feedPosts)
    }


    companion object {
        const val RETRY_TIMEOUT_MILLIS = 3000L
    }

}

Теперь везде , где используем репозитории - используем UseCase-ы
Пока создание репозитория на presentation слое оставляем как есть

class MainViewModel(application: Application) : AndroidViewModel(application = application) {

    private val repository = NewsFeedRepositoryImpl()
    
    private val getAuthStateFlowUseCase = GetAuthStateFlowUseCase(repository = repository)<--------------------

    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository = repository)<--------------------


    val authState = getAuthStateFlowUseCase() <--------------------

   fun login(){
        Log.d("MainViewModel", "login")
        viewModelScope.launch {
            val vkAuthCallback = object : VKIDAuthCallback {
                override fun onAuth(accessToken: AccessToken) {
                    val token = accessToken.token
                    Log.d("MainViewModel", "Success token = $token")
                    viewModelScope.launch {
                        checkAuthStateUseCase()<--------------------

                    }

                }

                override fun onFail(fail: VKIDAuthFail) {

                    Log.d("MainViewModel", "VKIDAuthFail = $fail")
                    viewModelScope.launch {
                        checkAuthStateUseCase()<--------------------

                    }
                    .........
                }
            }

            val initializer = VKIDAuthParams.Builder().apply {
                scopes = setOf("wall","friends")

            }.build()

            VKID.instance.authorize(callback =  vkAuthCallback,
                params = initializer)
        }
    }

}


class NewsFeedViewModel : ViewModel() {


    val exceptionHandler = CoroutineExceptionHandler { _, _, ->
        ///log

    }
    private val repository = NewsFeedRepositoryImpl()

    private val getFeedPostsListUseCase = GetFeedPostsListUseCase(repository = repository)<--------------------
    private val loadNextDataUseCase = LoadNextDataUseCase(repository = repository)<--------------------
    private val changeLikeStatusUseCase = ChangeLikeStatusUseCase(repository = repository)<--------------------
    private val deletePostUseCase = DeletePostUseCase(repository = repository)<--------------------
	................
	}
	
class CommentsViewModel(val feedPost: FeedPost) : ViewModel() {
    val repository = NewsFeedRepositoryImpl()

    private val getCommentsUseCase = GetCommentsUseCase(repository = repository)<--------------------

    val state = getCommentsUseCase(feedPost = feedPost)<--------------------
        .map { CommentsScreenState.Comments(feedPost = feedPost, comments = it) as CommentsScreenState }
        .onStart {
            emit(CommentsScreenState.Initial)
        }



}

#9.5 Dependency Injection in NewsFeedClient


 alias(libs.plugins.jetbrains.kotlin.kapt)

implementation(libs.dagger)
kapt(libs.dagger.compiler)!!!

..................
dagger = "2.51.1"

[libraries]
dagger = { group = "com.google.dagger", name = "dagger", version.ref = "dagger" }
dagger-compiler = { group = "com.google.dagger", name = "dagger-compiler", version.ref = "dagger" }
[plugins]
jetbrains-kotlin-kapt = { id = "org.jetbrains.kotlin.kapt", version.ref = "kotlin" }

1) Итак, на все классы навесим @Inject constructor, где необходимо

Сначала для всех юзкейсов

Entity не инжектим

Репозиторий будем инжектить внутри модуля

class  NewsFeedRepositoryImpl @Inject constructor() : NewsFeedRepository{

ApiFactory и ApiService не трогаем!

Добавляем в mapper и ViewModel-и

2) Пакет di/ApplicationComponent

@Component
interface ApplicationComponent {

}


@Module
interface DataModule {

    @Binds
    fun bindRepository(impl : NewsFeedRepositoryImpl) : NewsFeedRepository
}

В NewsFeedRepositoryImpl все зависимости выносим в конструктор


@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository
    
    companion object{
        @Provides
        fun providesApiService() : ApiService{
            return ApiFactory.apiService
        }
    }

}


Добавляем @Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope()

Чтобы было понятно, что время жизни компонентов, помеченных этой аннотацией = времени жизни приложения

@ApplicationScope <-----------------------------
@Component(modules = [DataModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance context : Context
        ) : ApplicationComponent
    }
}


@Module
interface DataModule {
    @ApplicationScope <-----------------------------
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object{
        @ApplicationScope <-----------------------------
        @Provides
        fun providesApiService() : ApiService{
            return ApiFactory.apiService
        }
        @ApplicationScope <-----------------------------
        @Provides
        fun vkAccessToken() : AccessToken? {
            return VKID.instance.accessToken
        }
    }

}
Инициаллизируем в App

class App : Application() {

    val component: ApplicationComponent by lazy {
        TODO()

    }
	
Далее переходим к ViewModel-ям

@ApplicationScope <---------------
class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards
    Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}

Аннотация для ключа


@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelKey(val value : KClass<out ViewModel>)


Новый модуль:

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel) : ViewModel

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel) : ViewModel

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel) : ViewModel
}

Теперь нужно доработать каждую ViewModel

class MainViewModel @Inject constructor(
    private val getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    val authState = getAuthStateFlowUseCase()
.................

class NewsFeedViewModel @Inject constructor(
    private val getFeedPostsListUseCase : GetFeedPostsListUseCase,
            private val loadNextDataUseCase :LoadNextDataUseCase,
            private val changeLikeStatusUseCase : ChangeLikeStatusUseCase,
            private val deletePostUseCase : DeletePostUseCase,
): ViewModel() {
.......................

class CommentsViewModel @Inject constructor(
    val feedPost: FeedPost, <--------------------------  эту проблему решим позже
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {




Далее

class MainActivity : ComponentActivity() {

    private val component by lazy{ <---------------------
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory <---------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this) <---------------------  добавим его в ApplicationComponent
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory) <--------------------- добавляем factory
                val authState = viewModel.authState.collectAsState(AuthState.Initial)
                when (authState.value) {
                    AuthState.Authorized -> MainScreen()
                    AuthState.NotAuthorized -> LoginScreen { viewModel.login() }
                    else -> {}
                }
            }
        }
    }
	
	
Далее factory передаём во все функции где используем ViewModel

@Composable
fun NewsFeedScreen(
    factory : ViewModelFactory,
    paddingValues : PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
){
...

@Composable
fun CommentsScreen(
    factory : ViewModelFactory,
    onBackPressed : () -> Unit,
    feedPost: FeedPost
){
    Log.d("111","feedPost = $feedPost")
    val viewModel: CommentsViewModel = viewModel(
        factory = factory// CommentsViewModalFactory(feedPost) <--------------- разберём в следующем уроке
    )
	
	
	class App : Application() {

    val component: ApplicationComponent by lazy {

        DaggerApplicationComponent.factory().create( <------------- временное решение
            this, FeedPost(
                id = 0,
                communityId = 0,
                communityName = "",
                publicationDate = "",
                communityImageUrl = "",
                contentText = "",
                contentImageUrl = "",
                statistics = emptyList(),
                isLiked = true
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        VKID.init(this)


    }
}

#9.6 Dependency Injection VkNewsClient Part 2

Раньше FeedPost передавался в фабрике, сейчас нигде не ипользуется

@Composable
fun CommentsScreen(
    factory : ViewModelFactory,
    onBackPressed : () -> Unit,
    feedPost: FeedPost
){
    Log.d("111","feedPost = $feedPost")
    val viewModel: CommentsViewModel = viewModel(
        factory = factory// CommentsViewModalFactory(feedPost)
    )
......

Будем исправлять при помощи сабкомпонентов
Component на момент сборки должен содержать все зависимости, которые будет предоставлять.


FeedPost нужен для создания CommentsViewMode
При создании приложения мы не знаем объект FeedPost, поэтому передавать его в компонент нет никакого смысла
Он  известен только при переходе на экран комментариев и в этот момент мы можем закинуть его в граф зависемостей


@Subcomponent
interface CommentsScreenComponent {
    
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}


@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    
    fun getCommentsScreenComponentFactory() : CommentsScreenComponent.Factory <---------------

    fun inject(activity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance feedPost: FeedPost
        ): ApplicationComponent
    }
}

Далее

@Module
interface ViewModelModule { - Здесь мы указали, что все эти модели должны быть собраны в одну коллекцию map на момент компиляции. Но при этом всех параметров для CommentsViewModel
							---->> на момент компиляции нет. Поэтому использовать CommentsViewModel здесь не будем

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel) : ViewModel

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel) : ViewModel

    @IntoMap
    @ViewModelKey(CommentsViewModel::class) <<<<<<<<<<<<<------------------------ удаляем
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel) : ViewModel
}

Создаём отдельный модуль

@Module
interface CommentsViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel) : ViewModel
}

Который передаём в сабкомпонент


@Subcomponent(modules = [CommentsViewModelModule::class]) <<<<------------------
interface CommentsScreenComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}

Далее создаём Subcomponent в CommentsScreen

 LocalContext.current.applicationContext <---------------------- получение контекста


@Composable
fun CommentsScreen(
    factory: ViewModelFactory,
    onBackPressed: () -> Unit,
    feedPost: FeedPost
) {
    Log.d("111", "feedPost = $feedPost")

    val component =
        (LocalContext.current.applicationContext as App).component.getCommentsScreenComponentFactory()
            .create(
                feedPost = feedPost
            )
			
	val viewModel: CommentsViewModel = viewModel(
        factory = component.getViewModelFactory() <------------------смотри ниже
    )
	......
	
Единственная сложность в Compose не заинжектить переменную в поле, поэтому добавляем функцию getViewModelFactory()

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {
    
    fun getViewModelFactory() : ViewModelFactory <--------------------

    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}

При этом 


@ApplicationScope <--------------------- нужно убрать, чтобы не создавать один и тот же объект фабрики, а создавать разные
class ViewModelFactory @Inject constructor(
    private val viewModelProviders: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProviders[modelClass]?.get() as T
    }
}
			
			
#9.7 Immutable and recomposition

Реализуем возможность получать component при помощи Composable-функции и использовать везде в приложении

В файле App не классе создаём функцию


@Composable
fun getApplicationComponent() : ApplicationComponent{
    return (LocalContext.current.applicationContext as App).component    
}

И далее везде её используем ------>>>


@Composable
fun CommentsScreen(
    onBackPressed: () -> Unit,
    feedPost: FeedPost
) {

    val component = getApplicationComponent().getCommentsScreenComponentFactory() <----------------
        .create(
            feedPost = feedPost
        )
    val viewModel: CommentsViewModel = viewModel(
        factory = component.getViewModelFactory()
    )
................

class MainActivity : ComponentActivity() {

    private val component by lazy{ <<<---------remove
        (application as App).component
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory <<<---------remove

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)<<<---------remove
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
----------------------------->>>>>>>>>>>>>>>>>>> меняем на 

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val component = getApplicationComponent() <<<---------add
                val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory()) <<<---------add
                val authState = viewModel.authState.collectAsState(AuthState.Initial)
                when (authState.value) {
                    AuthState.Authorized -> MainScreen()<<<---------remove factory
                    AuthState.NotAuthorized -> LoginScreen { viewModel.login() }
                    else -> {}
                }
            }
        }
    }
	
	И т.д.
	
Далее Посмотрим 

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            VkNewsClientTheme {
                val component = getApplicationComponent()
                val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
                val authState = viewModel.authState.collectAsState(AuthState.Initial)
                when (authState.value) {
                    AuthState.Authorized -> MainScreen()
                    AuthState.NotAuthorized -> LoginScreen { viewModel.login() }
                    else -> {}
                }
            }
        }
    }
	
	При смене стейта происходит рекомпозиция. И component, viewModel authState создаются каждый раз занова. Их можно вынести из Composable-функции
	
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            val component = getApplicationComponent() <<<<<----вынесли из VkNewsClientTheme
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())<<<<<----вынесли из VkNewsClientTheme
            val authState = viewModel.authState.collectAsState(AuthState.Initial)<<<<<----вынесли из VkNewsClientTheme
            VkNewsClientTheme {
                when (authState.value) {
                    AuthState.Authorized -> MainScreen()
                    AuthState.NotAuthorized -> LoginScreen { viewModel.login() }
                    else -> {}
                }
            }
        }
    }
	
Также меняем
	
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
) {

    val component = getApplicationComponent()
    val viewModel: NewsFeedViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial)

    when (val currentState = screenState.value) {
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts, viewModel = viewModel,
                paddingValues = paddingValues,
                onCommentsClickListener = onCommentsClickListener,
                nextDataIsLoading = currentState.nextDataIsLoading
            )
        }

        NewsFeedScreenState.Initial -> {

        }

        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}

--------------------------------->>>>>>>>>>>>>>>>>>>>>


@Composable
fun NewsFeedScreen(
    paddingValues: PaddingValues,
    onCommentsClickListener: (FeedPost) -> Unit
) {

    val component = getApplicationComponent()
    val viewModel: NewsFeedViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.screenState.collectAsState(NewsFeedScreenState.Initial) <--------------state получаем здесь
    NewsFeedScreenContent( <<<--- рекомпозиция только здесь
        screenState = screenState,
        paddingValues = paddingValues,
        onCommentsClickListener = onCommentsClickListener,
        viewModel = viewModel
    )
   


}

@Composable
private fun NewsFeedScreenContent(screenState : State<NewsFeedScreenState>,
                                  paddingValues: PaddingValues,
                                  onCommentsClickListener: (FeedPost) -> Unit,
                                  viewModel: NewsFeedViewModel){
    when (val currentState = screenState.value) { <----------------- Но value получаем только внутри этой функции. Поэтому при рекомпозиции меняется только она
        is NewsFeedScreenState.Posts -> {
            FeedPosts(
                posts = currentState.posts, viewModel = viewModel,
                paddingValues = paddingValues,
                onCommentsClickListener = onCommentsClickListener,
                nextDataIsLoading = currentState.nextDataIsLoading
            )
        }

        NewsFeedScreenState.Initial -> {

        }

        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}


CommentScreen поменяем также


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(
    onBackPressed: () -> Unit,
    feedPost: FeedPost
) {

    val component = getApplicationComponent().getCommentsScreenComponentFactory()
        .create(
            feedPost = feedPost
        )
    val viewModel: CommentsViewModel = viewModel(
        factory = component.getViewModelFactory()
    )

    val screenState = viewModel.state.collectAsState(initial = CommentsScreenState.Initial)
    
    CommentsScreenContent(screenState, onBackPressed)
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreenContent(screenState : State<CommentsScreenState>,
                          onBackPressed: () -> Unit){
    val currentState = screenState.value

    val viewModelCurrentState = LocalViewModelStoreOwner.current?.viewModelStore
    if (currentState is CommentsScreenState.Comments) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.title_comments_screen))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModelCurrentState?.clear()
                            onBackPressed()
                        }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)

                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 72.dp
                ),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(currentState.comments, key = { it.id }) {
                    CommentItem(comment = it)
                }
            }

        }
    }
}

Далее: Экран комментариев может переживать рекомпозицию , т.к. находится в графе навигации из-за изменения объекта feedPost, хотя он может быть тот же
FeedPost - это data class = equal проверяется только по первичным полям, но этого недостаточно, поэтому ЕСЛИ КЛАСС ВНУТРИ НЕ ИЗМЕНЯЕТСЯ при одинаковых 
первичных полях и все поля VAL!!! его обозначают аннотацияей @Immutable

@Immutable <<<-------
@Parcelize
data class FeedPost(
    val id: Long,
    val communityId : Long,
    val communityName: String,
    val publicationDate: String = "14:00",
    val communityImageUrl: String = "",
    val contentText: String = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
    val contentImageUrl: String?,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 234),
        StatisticItem(StatisticType.COMMENTS, 113),
        StatisticItem(StatisticType.SHARES, 34),
        StatisticItem(StatisticType.LIKES, 243)
    ),
    val isLiked : Boolean = Random.nextBoolean()
) : Parcelable{
