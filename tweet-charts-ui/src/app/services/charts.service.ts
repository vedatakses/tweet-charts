import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class ChartsService {
  private userProfileUrl: string = "http://localhost:8036/charts/profile/";

  constructor(private http: Http) { }

  getUserProfile(username: any): any {
    console.log('Retrieving user profile of', username);
    let requestUrl = this.userProfileUrl + username;
    let requestHeader = new Headers();
    requestHeader.append('Content-Type', 'application/json');

    let options = new RequestOptions({
      headers: requestHeader,
    });

    return this.http.get(requestUrl, options)
      .map(result => {
        return <any[]>result.json();
      })
  }
}
