import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ChartsComponent } from './charts/charts.component';
import {
  MatInputModule,
  MatToolbarModule,
  MatIconModule,
  MatButtonModule,
} from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    ChartsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    RouterModule.forRoot([
      { path: '', component: WelcomeComponent },
      { path: 'charts/:username', component: ChartsComponent }
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
