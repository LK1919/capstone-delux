package ai.deepar.deepar_example.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ai.deepar.deepar_example.R
import ai.deepar.deepar_example.databinding.ActivityNewsBinding
import ai.deepar.deepar_example.patient.adapter.NewsListCustomAdapter
import ai.deepar.deepar_example.patient.models.NewsData
import ai.deepar.deepar_example.patient.services.WebNewsService
import android.content.Intent
import android.widget.ListView

class NewsActivity : AppCompatActivity() {
  lateinit var binding : ActivityNewsBinding
  lateinit var newsListView : ListView
  val newsService = WebNewsService()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityNewsBinding.inflate(layoutInflater)
    setContentView(binding.root)
//    newsListView = binding.newsListView

    val runnable = Runnable {
      val adapter = NewsListCustomAdapter(this,newsService.newsList())
      runOnUiThread {
        newsListView.adapter = adapter
      }

      newsListView.setOnItemClickListener { adapterView, view, i, l ->
        val selectedNews = newsListView.getItemAtPosition(i) as NewsData
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra("href", selectedNews.href)
        startActivity(intent)
      }
    }
    Thread(runnable).start()
  }
}