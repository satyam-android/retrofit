package com.base.company.retrofittest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Api api;
HttpLoggingInterceptor httpLoggingInterceptor;
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

         retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                 .client(okHttpClient)
                .build();
        api = retrofit.create(Api.class);
//         getPosts();
//         getComments();
        uploadPost();
    }

    private void getComments() {
        Call<List<Comment>> listCall = api.getComments(4);
        listCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.code() == 200) {
                    List<Comment> comments = response.body();
                    String content = "";
                    for (Comment comment : comments) {

                        content += "\n\n ID: " + comment.getId() + "\n" + " body " + comment.getText() + "\n" + " Title " + comment.getEmail();

                        ((TextView) findViewById(R.id.tv_response)).setText(content);

                    }
                } else {
                    ((TextView) findViewById(R.id.tv_response)).setText(String.valueOf(response.code()));

                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                ((TextView) findViewById(R.id.tv_response)).setText(t.getMessage());

            }
        });
    }

    public void uploadPost() {
        //Call by passing Serialized Object
//        Post post = new Post(23, "New Title", "New Text");
//        Call<Post> call = api.createPost(post);

        //Call by passing all parameter Separately
        Call<Post> call = api.createPost(23,"New Parameter Title"," New Parameter Text");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    ((TextView) findViewById(R.id.tv_response)).setText(String.valueOf(response.code()));
                    return;
                }
                    Post postResponse=response.body();
                    String content="\n\n" +"Code "+response.code()+"\n "+" Title "+postResponse.getTitle();
                ((TextView) findViewById(R.id.tv_response)).setText(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                ((TextView) findViewById(R.id.tv_response)).setText(t.getMessage());
            }
        });
    }

    private void getPosts() {
//        Call<List<Post>> call = api.getPosts();
        Call<List<Post>> call = api.getPosts(4, "id", "desc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.code() == 200) {
                    List<Post> posts = response.body();
                    String content = "";
                    for (Post post : posts) {

                        content += "\n\n ID: " + post.getId() + "\n" + " body " + post.getText() + "\n" + " Title " + post.getTitle();

                        ((TextView) findViewById(R.id.tv_response)).setText(content);

                    }
                } else {
                    ((TextView) findViewById(R.id.tv_response)).setText(String.valueOf(response.code()));

                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                ((TextView) findViewById(R.id.tv_response)).setText(t.getMessage());

            }
        });
    }
}
