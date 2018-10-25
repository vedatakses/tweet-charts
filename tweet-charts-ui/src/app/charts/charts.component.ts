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
  private username: any;
  public profile: UserProfile;
  chart = [];

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

    // for tweet and follower count charts
    this.chartService.getLastProfiles(this.username)
      .subscribe(result => {
        console.log(result);
        
        let followerCount = result.map(element => element.followerCount);
        let timestamp = result.map(element => element.timestamp);

        this.chart = new Chart('canvas', {
          type: 'line',
          data: {
            labels: timestamp.reverse(),
            datasets: [
              { 
                data: followerCount,
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
