package jp.techinstitute.ti_021.tryman;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    MediaPlayer mPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 再生ボタン部品を取得する。
        Button btn = (Button)findViewById(R.id.btn_play);
        // 再生ボタンにアクションを追加する。
        btn.setOnClickListener(new Play());

        // アダプターに項目を追加する
        ArrayAdapter adpMusicPlay = ArrayAdapter.createFromResource(this, R.array.music_play, android.R.layout.simple_spinner_item);
        adpMusicPlay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // スピナーにアダプターを設定する
        Spinner spnMediaPlay = (Spinner)findViewById(R.id.spn_music);
        spnMediaPlay.setAdapter(adpMusicPlay);

    }

    private class Play implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Spinner spn = (Spinner)findViewById(R.id.spn_music);
            String strText = (String)spn.getSelectedItem();
            Toast.makeText(MainActivity.this, strText, Toast.LENGTH_SHORT).show();
            int intPlayMusic = 0;

            Button btn = (Button)findViewById(R.id.btn_play);
            String strBtnText = (String)btn.getText();

            // 再生ボタンの場合、スピナーで選択された曲を再生する。
            if("再生".equals(strBtnText)) {
                // スピナーで選択された音楽変数に設定する
                if ("バレンタイン曲".equals(strText)) {
                    intPlayMusic = R.raw.music;
                } else if ("川の曲".equals(strText)) {
                    // メディアプレイヤーのインスタンスを取得する。
                    intPlayMusic = R.raw.music2;
                } else {
                    // 曲が選択されなければ終了する。
                    stopMusic();
                    Toast.makeText(MainActivity.this, "曲が選択されていないよ。", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 選択した音楽を再生する。
                playMusic(intPlayMusic);
                // ボタン名を再生 ⇒停止に変換する。
                btn.setText("停止");
            } else {
                // 停止ボタンの場合、再生中音楽を停止する
                stopMusic();
                // ボタン名を停止 ⇒再生に変換する。
                btn.setText("再生");
            }
        }
    }

    private void playMusic (int intPlayMusic) {
        // 曲がすでに再生されていたら停止する。
        stopMusic();
        // 引数に設定された曲を再生する。
        mPlayer = MediaPlayer.create(MainActivity.this, intPlayMusic);
        mPlayer.start();

    }

    private void stopMusic(){
        // MediaPlayerが再生されていたら停止する。
        if(mPlayer != null){
            if(mPlayer.isPlaying()){
                mPlayer.stop();
            }
            mPlayer.release();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMusic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
