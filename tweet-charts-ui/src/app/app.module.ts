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
    HttpModule,
    HttpClientModule,
    MatInputModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    RouterModule.forRoot([
      { path: '', component: WelcomeComponent },
      { path: 'charts/:username', component: ChartsComponent }
    ])
  ],
  providers: [ChartsService],
  bootstrap: [AppComponent]
})
export class AppModule { }
