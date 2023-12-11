package com.uMatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class MainOnboarding extends AppCompatActivity {

    public ContainerOnboarding containerOnboarding;
    public LinearLayout indicatorDots;
    public Button btnNext, btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_onboarding);
        indicatorDots = findViewById(R.id.indicatorDots);
        btnSkip = findViewById(R.id.btnSkip);
        btnNext = findViewById(R.id.btnNext);
        onboardingSetup();
        ViewPager2 viewOnboarding = findViewById(R.id.viewOnboarding);
        viewOnboarding.setAdapter(containerOnboarding);
        indicatorSetup();
        indicatorON(0);

        viewOnboarding.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                indicatorON(position);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LogIn.class));
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewOnboarding.getCurrentItem() + 1 < containerOnboarding.getItemCount()) {
                    viewOnboarding.setCurrentItem(viewOnboarding.getCurrentItem() + 1);
                }else {
                    startActivity(new Intent(getApplicationContext(), LogIn.class));
                    finish();
                }
            }
        });

    }

    private void onboardingSetup(){
        List <ItemOnboarding> itemOnboardings = new ArrayList<>();
        ItemOnboarding firstOnboarding = new ItemOnboarding();
        firstOnboarding.setTitle("Merasa Hancur?!?");
        firstOnboarding.setDesc("Putus dengan pasanganmu? karena tidak sejalan atau sulit untuk menjalankannya lagi?");
        firstOnboarding.setImage(R.drawable.firstimage);

        ItemOnboarding secondOnboarding = new ItemOnboarding();
        secondOnboarding.setTitle("Ciptakan Hubungan Baru!");
        secondOnboarding.setDesc("Carilah pasanganMu dan segera untuk membuat hubungan yang baru ");
        secondOnboarding.setImage(R.drawable.secondimage);

        ItemOnboarding thirdOnboarding = new ItemOnboarding();
        thirdOnboarding.setTitle("Kekasihku!");
        thirdOnboarding.setDesc("Mulailah saling berbagi demi membangun perasaan untuk mencintai");
        thirdOnboarding.setImage(R.drawable.thirdimage);

        itemOnboardings.add(firstOnboarding);
        itemOnboardings.add(secondOnboarding);
        itemOnboardings.add(thirdOnboarding);

        containerOnboarding = new ContainerOnboarding(itemOnboardings);
    }

    public void indicatorSetup(){
        ImageView[] dots = new ImageView[containerOnboarding.getItemCount()];
        LinearLayout.LayoutParams lP = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        lP.setMargins(10,0,10,0);
        for(int i = 0; i < dots.length; i++){
            dots[i] = new ImageView(getApplicationContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_off));
            dots[i].setLayoutParams(lP);
            indicatorDots.addView(dots[i]);
        }
    }

    public void indicatorON(int indicator){
        int dotsCount = indicatorDots.getChildCount();
        for(int i = 0; i < dotsCount; i++){
            ImageView image = (ImageView) indicatorDots.getChildAt(i);
            if (i == indicator) {
                image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.indicator_on));
            }else{
                image.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.indicator_off));
            }
        }
        if (indicator == containerOnboarding.getItemCount() - 1) {
            btnNext.setText("Start");
        } else {
            btnNext.setText("Next");
        }
    }
}