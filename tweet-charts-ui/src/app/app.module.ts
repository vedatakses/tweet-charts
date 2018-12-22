import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http'
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ChartsComponent } from './charts/charts.component';

import { ChartsService } from './services/charts.service';

import {
  MatInputModule,
  MatToolbarModule,
  MatIconModule,
  MatButtonModule,
  MatProgressSpinnerModule
} from '@angular/material';
import { TweetcountComponent } from './tweetcount/tweetcount.component';
import { MentionsComponent } from './mentions/mentions.component';
import { SentimentsComponent } from './sentiments/sentiments.component';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    ChartsComponent,
    TweetcountComponent,
    MentionsComponent,
    SentimentsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpModule,
    HttpClientModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    RouterModule.forRoot([
      { path: '', component: WelcomeComponent },
      { path: 'charts/:username', component: ChartsComponent },
      { path: 'charts/:username/tweets-chart', component: TweetcountComponent},
      { path: 'charts/:username/mentions-chart', component: MentionsComponent},
      { path: 'charts/:username/sentiments-chart', component: SentimentsComponent}
    ])
  ],
  providers: [ChartsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
