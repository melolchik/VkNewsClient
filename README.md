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