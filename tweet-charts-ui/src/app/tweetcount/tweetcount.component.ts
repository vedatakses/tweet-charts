import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ChartsService } from '../services/charts.service';
import { UserProfile } from '../model/UserProfile.model';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-tweetcount',
  templateUrl: './tweetcount.component.html',
  styleUrls: ['./tweetcount.component.css']
})
export class TweetcountComponent implements OnInit {
  username: any;
  profile: UserProfile;
  chartTweet = [];

  constructor(private route: ActivatedRoute,
    private router: Router,
    private chartService: ChartsService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);

    // for last tweet and followers counts
    this.chartService.getUserProfile(this.username)
      .subscribe(result => {
        console.log(result);
        this.profile = result;
      });

    // for tweet count charts
    this.chartService.getLastProfiles(this.username)
      .subscribe(result => {
        console.log(result);

        let countTweet = result.map(element => element.tweetCount);
        let dateStr = result.map(element => new Date(element.timestamp * 1000).toLocaleString());

        this.chartTweet = new Chart('canvas', {
          type: 'line',
          data: {
            labels: dateStr.reverse(),
            datasets: [
              {
                data: countTweet.reverse(),
                borderColor: "#3cba9f",
                fill: false
              },

            ]
          },
          options: {
            legend: {
              display: false
            },
            scales: {
              xAxes: [{
                display: true
              }],
              yAxes: [{
                display: true
              }],
            }
          }
        });
      });
  }

  generateFollowersChart() {
    this.router.navigate(['charts', this.username]);
  }

}
