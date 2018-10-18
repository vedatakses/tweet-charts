import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class ChartsService {
  private profileUrl: string = "http://localhost:8036/charts/profile/";
  private lastProfilesUrl: string = "http://localhost:8036/charts/counts/amazon";
  private sentimentsUrl: string = "http://localhost:8036/charts/sentiments/amazon";
  private lastLocationsUrl: string = "http://localhost:8036/charts/locations/amazon";
  private oembedTweetUrl: string = "http://localhost:8036/charts/oembed/amazon";

  private requestHeader: any;
  private requestOptions: any;

  constructor(private http: Http) {
    this.requestHeader = new Headers().append('Content-Type', 'application/json');
    this.requestOptions = new RequestOptions({ headers: this.requestHeader });
  }

  getUserProfile(username: string): any {
    console.log('Retrieving user profile of', username);
    let requestUrl = this.profileUrl + username;

    return this.http.get(requestUrl, this.requestOptions)
      .map(result => {
        return <any[]>result.json();
      });
  }

  getLastProfiles(username: string): any {
    console.log('Retrieving last profiles of', username);
    let requestUrl = this.lastProfilesUrl + username;

    return this.http.get(requestUrl, this.requestOptions)
      .map(result => {
        return <any[]>result.json();
      });
  }

  getLastMetionsSentiments(username: string): any {
    console.log('Retrieving last sentiments of', username);
    let requestUrl = this.sentimentsUrl + username;

    return this.http.get(requestUrl, this.requestOptions)
      .map(result => {
        return <any[]>result.json();
      });
  }

  getLastMetionsLocations(username: string): any {
    console.log('Retrieving mentioners locations of', username);
    let requestUrl = this.lastLocationsUrl + username;

    return this.http.get(requestUrl, this.requestOptions)
      .map(result => {
        return <any[]>result.json();
      });
  }

  getLastMentionOEmbedHtml(username: string): any {
    console.log('Retrieving last mentions oembed link of', username);
    let requestUrl = this.oembedTweetUrl + username;

    return this.http.get(requestUrl, this.requestOptions)
      .map(result => {
        return <any[]>result.json();
      });
  }

}
