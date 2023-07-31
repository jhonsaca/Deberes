package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import database.Persona;
import ec.edu.tecnologicoloja.rom_listas.R;

public class ListAdapter extends ArrayAdapter<Persona>{
    private Context context;
    private ArrayList<Persona> list;
    public ListAdapter(Context context, ArrayList<Persona> list){
        super(context, R.layout.content_list);
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount(){
        return list.size();
    }
    @Override
    public View getView(int position, View convertview, ViewGroup parent){
        View view;
        final ViewHolder viewHolder;
        if (convertview == null || convertview.getTag()==null){
            viewHolder= new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.content_list,parent,false);
            viewHolder.vItemName = (TextView) view.findViewById(R.id.txtcon);
            viewHolder.vItemApe = (TextView) view.findViewById(R.id.txtcon2);
            viewHolder.vItemImage = (ImageView) view.findViewById(R.id.img);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertview.getTag();
            view = convertview;
        }
        viewHolder.vItemName.setText(list.get(position).getNombre());
        viewHolder.vItemApe.setText(list.get(position).getApellido());
        Glide.with(context).load("https://i.imgur.com/DvpvklR.png").into(viewHolder.vItemImage);
        return view;
    }

    static class ViewHolder{
        protected TextView vItemName;
        protected TextView vItemApe;
        protected ImageView vItemImage;
    }
}
