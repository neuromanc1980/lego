package com.example.xavib.lego;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PartDownloader extends AsyncTask<String, Void, List<Part>> {

    private Context context;
    private ListView listview;

    public PartDownloader(Context context, ListView listview) {

        this.context = context;
        this.listview  = listview;

    }

    public ArrayList<Part> llista = new ArrayList();


    private ProgressDialog pDialog;

    @Override protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Searching. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);
        pDialog.setTitle(R.string.please_wait);
        String msg = context.getResources().getString(R.string.searching);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override protected List<Part> doInBackground(String... params) {



        int count;
        //exemple format "https://rebrickable.com/api/get_set_parts?key=Drx9VlnnCV&format=tsv&set=60128-1";
        String  url2 = params[0];
        try {

            URL url = new URL(url2);
            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            pDialog.setMax(lengthOfFile);
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) { //-1 indica final de fitxe
                total += count;
                output.write(data, 0, count); //descàrrega
            }

            //guardem l'arxiu en un string
            String xml = new String(output.toByteArray());

            input.close();
            output.flush();

            Log.d("xxx","el string original:\n "+xml);

            //creem un directori, i un arxiu i el netejem
            File dir = context.getExternalFilesDir(null);


            //extreiem les línies
            String linia[] = xml.split("\n");

            for (String l: linia){



                //per a cada linia, extreiem les columnes
                String columna[] = l.split("\t");
                int contador = 0;

                Part part = new Part(columna[1], columna[4], columna[5], columna[2], columna[8]);

                //saltem la primera línia que conté la descricpcio de les columnes
                if (!columna[2].equals("ldraw_color_id") ) {   llista.add(part); }
                Log.d("xavi", columna[2]);
                //id color tipus quantitat imatge

            }


        } catch (Exception e) {
            Log.e("Errorl: ", e.getMessage());
        }

        //items a l'array?
        Log.d("xxx","parts a l'array:\n "+llista.size());
        return llista;
    }

    protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override public void onPostExecute(List<Part> llista) {
        pDialog.dismiss();

        CatalogAdapter adapter = new CatalogAdapter (context,llista);
        listview.setAdapter(adapter);

      //  if (listener != null) listener.onCurrenciesLoaded(llista);
    }
}

class CatalogAdapter extends BaseAdapter {

    private Context context; //permet accedir a recursos del sistema
    private List<Part> catalog;

    public CatalogAdapter(Context context, List<Part> catalog) {
        this.context = context;
        this.catalog = catalog;
    }

    @Override
    public int getCount() {  return catalog.size();    }

    @Override
    public Object getItem(int position) {  return catalog.get(position);       }

    @Override //reciclatge vistes
    public long getItemId(int position) {
        return position;  }

    //public Part(String id, String color, String tipus, String quantitat, String imatge)

    public class ViewHolder {
        public TextView id;
        public TextView color;
        public ImageView imatge;
        public TextView tipus;
        public TextView quantitat;
    }

    @Override //codi que desplega la llista
    public View getView(int position, View convertView, ViewGroup parent) {

        //reciclats de vista. estalvia lectures de xmpl
        View myView = convertView;
        if (myView == null){

            //agafo el view que em ve. si no està inflat l'utilitzo, si ja està inflat doncs inflo
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.part, parent, false);
            ViewHolder holder = new ViewHolder();

            holder.id = (TextView) myView.findViewById(R.id.id);
            holder.color = (TextView) myView.findViewById(R.id.color);
            holder.imatge = (ImageView) myView.findViewById(R.id.imatge);
            holder.tipus = (TextView) myView.findViewById(R.id.tipus);
            holder.quantitat = (TextView) myView.findViewById(R.id.quantitat);
            myView.setTag(holder);  //es pot fer a qualsevol tipus d'objecte
            // el findviewById ja estarà fet i no es repetirà
        }
        ViewHolder holder = (ViewHolder) myView.getTag(); //obtenim el tag tant si l'acabo d'asignar o no

        Part part = catalog.get(position);

        String id = "ID: "+part.getId();
        holder.id.setText(id);

        String color = "COLOR:"+part.getColor();
        holder.color.setText(color);

        String tipus = "TYPE: "+part.getTipus();
        holder.tipus.setText(tipus);

        String quantitat = "QUANTITY: "+part.getQuantitat();
        holder.quantitat.setText(quantitat);

        Picasso.with(context).load(part.getImage()).into(holder.imatge);
        //Image imatge = part.getImage().;
        //holder.imatge.setImageResource(imatge);
        return myView;
        //invlem i returnem el layout cada cop q hi ha un item a la llista
    }
}