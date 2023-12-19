package com.projects.bankpro.di.components;


import com.projects.bankpro.ui.activities.MainActivity;


//@Singleton
//@Component(modules = { /* Include your modules here */ })
public interface DaggerComponent {
    // Add your injection methods here for activities, fragments, etc.
    void inject(MainActivity mainActivity);
    // Add other injection methods as needed
}
