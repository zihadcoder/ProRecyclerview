package com.example.prorecycle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class InsFragment extends Fragment {

    RecyclerView recyclerView;
    HashMap<String,String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview =inflater.inflate(R.layout.fragment_ins, container, false);
        recyclerView =myview.findViewById(R.id.recyclerView);

        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ////////////////main oncreate //////////////////////


        String url = "https://hlwokbye.000webhostapp.com/app/ins.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray= response.getJSONArray("upload");

                    for (int x =0; x<jsonArray.length() ;x++){

                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        String profile = jsonObject.getString("profile");
                        String name = jsonObject.getString("name");
                      //  String pic = jsonObject.getString("pic");
                        String like = jsonObject.getString("like");
                        String caption = jsonObject.getString("caption");
                        String days = jsonObject.getString("days");


                        // String[] image = new String[]{jsonObject.getString("image")};
                        //String imagrArray = Arrays.toString(image);



                        hashMap = new HashMap<>();
                        hashMap.put("profile",profile);
                        hashMap.put("name",name);
                       // hashMap.put("pic",pic);
                        hashMap.put("like",like);
                        hashMap.put("caption",caption);
                        hashMap.put("days",days);



                        /// hashMap.put("image",imagrArray);
                        //hashMap.put("video_id",video_id);
                        arrayList.add(hashMap);


                        MyAdapter adapter = new MyAdapter();
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }





                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);


        return myview;

    }









    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private class MyViewHolder extends RecyclerView.ViewHolder{
            CircleImageView profile,profilecomment;
            TextView name,like,namecomment,caption,days;
            ImageView pic;

             /////////////////////////////////////////for global// identification//////////////////



            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                profile = itemView.findViewById(R.id.profile);
                profilecomment= itemView.findViewById(R.id.profilecomment);
                name = itemView.findViewById(R.id.name);
                like = itemView.findViewById(R.id.like);
              //  namecomment = itemView.findViewById(R.id.namecomment);
                caption = itemView.findViewById(R.id.caption);
                days = itemView.findViewById(R.id.days);
                pic = itemView.findViewById(R.id.pic);


             //////////////////////////////////////////for identification//////////////////

            }
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = getLayoutInflater();
            View myview  = inflater.inflate(R.layout.design,parent,false);
//////////////////for layout inflate /////////////////


            return new MyViewHolder(myview);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            // allllllll work here/////////////////////

            hashMap= arrayList.get(position);
            String prof= hashMap.get("profile");
            String nam=hashMap.get("name");
           // String pict =hashMap.get("pic");
            String lik =hashMap.get("like");
            String captio =hashMap.get("caption");
            String day =hashMap.get("days");



            Picasso.get().load(prof).placeholder(R.drawable.person).into(holder.profile);
            Picasso.get().load(prof).placeholder(R.drawable.person).into(holder.profilecomment);
            Picasso.get().load(prof).placeholder(R.drawable.person).into(holder.pic);

            holder.name.setText(nam);
            holder.like.setText(lik);
            holder.caption.setText(captio);
            holder.days.setText(day);








        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }





    }


}