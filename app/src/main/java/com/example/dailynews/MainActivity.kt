package com.example.dailynews

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import android.widget.SearchView.*
import android.widget.Toast

import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var adapter:NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        newsList.layoutManager = LinearLayoutManager(this@MainActivity)

        refreshLayout.setOnRefreshListener {
            getNews("")
        }

        getNews("")

    }

    private fun getNews(keyword:String) {

        refreshLayout.isRefreshing = true
        val news:Call<News>

//        news = if(keyword.isNotEmpty()){
//            NewsService.newInstance.getNewsSearch(keyword, "English", "publishedAt")
//        } else{
            news = NewsService.newInstance.getHeadlines("in",pageNum)
//        }

//        Log.d("TAG", "getNews: " + news.toString())

        news.enqueue(object : retrofit2.Callback<News>{

            override fun onResponse(call: Call<News>, response: Response<News>) {

                val news = response.body()
                if(news != null){
                    totalResults = news.totalResults
                   articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
                refreshLayout.isRefreshing = false
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                refreshLayout.isRefreshing = false
            }
        }
        )
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//       val inflater = menuInflater
//        inflater.inflate(R.menu.menu_search, menu)
//
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchItem = menu?. findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as SearchView
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = "Search Anything..."
//
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                if (query?.length!! > 2) {
//                    getNews(query)
//                    Toast.makeText(applicationContext, "Looking for $query", Toast.LENGTH_SHORT)
//                        .show()
//                }
//                return false;
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                getNews(newText!!)
//                Log.d("TAG", "onQueryTextChange: ${newText.toString()}")
//                return false;
//            }
//        }
//
//        )
//        searchItem.icon.setVisible(false,false)
//
//        return true
//    }
}