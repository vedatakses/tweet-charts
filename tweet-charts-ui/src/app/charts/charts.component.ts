import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { ChartsService } from '../services/charts.service';
import { UserProfile } from '../model/UserProfile.model';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent {
  username: any;
  profile: UserProfile;
  chartFollowers = [];

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
    console.log('Initializing charts for', this.username);

    // for last tweet and followers counts
    this.chartService.getUserProfile(this.username)
      .subscribe(result => {
        console.log(result);
        this.profile = result;
      });

    // for follower count charts
    this.chartService.getLastProfiles(this.username)
      .subscribe(result => {
        console.log(result);

        let countFollower = result.map(element => element.followerCount);
        let countTweet = result.map(element => element.tweetCount);
        let dateStr = result.map(element => new Date(element.timestamp * 1000).toLocaleString());

        this.chartFollowers = new Chart('canvas', {
          type: 'line',
          data: {
            labels: dateStr.reverse(),
            datasets: [
              {
                data: countFollower.reverse(),
                borderColor: "#3cba9f",
                fill: false
              },

            ]
          },
          options: {
            title: {
              display: true,
              text: 'Followers by Time'
            },
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

  generateTweetsChart() {
    this.router.navigate(['charts', this.username, 'tweets-chart']);
  }

  /*    // for last mentioners sentiments
      this.chartService.getLastMetionsSentiments(this.username)
        .subscribe(result => {
          console.log(result);
        });
  
      // for last mentioners locations
      this.chartService.getLastMetionsLocations(this.username)
        .subscribe(result => {
          console.log(result);
        });
  
      // for last mention oembed link
      this.chartService.getLastMentionOEmbedHtml(this.username)
        .subscribe(result => {
          console.log(result.html);
        }); * /
    }
  }
  */
}
