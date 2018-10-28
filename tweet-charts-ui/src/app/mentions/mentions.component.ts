import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ChartsService } from '../services/charts.service';
import { Chart } from 'chart.js';

@Component({
  selector: 'app-mentions',
  templateUrl: './mentions.component.html',
  styleUrls: ['./mentions.component.css']
})
export class MentionsComponent implements OnInit {
  username: any;
  locationsChart = [];

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
    let userLocations = [];
    let counts = [];
    let backgroundColors = [];

    // for last mentioners locations
    this.chartService.getLastMetionsLocations(this.username)
      .subscribe(result => {
        console.log(result);
        Object.keys(result).forEach(key => {
          userLocations.push(key);
          counts.push(result[key]);
          backgroundColors.push(this.generateBackgroundColor());
        })

        this.locationsChart = new Chart('canvas', {
          type: 'doughnut',
          data: {
            labels: userLocations,
            datasets: [{
              backgroundColor: backgroundColors,
              data: counts
            }]
          },
          options: {
            title: {
              display: true,
              text: 'Locations of Mentions'
            }
          }
        });
      });
  }

  generateTweetsChart() {
    this.router.navigate(['charts', this.username, 'tweets-chart']);
  }

  generateBackgroundColor() {
    var r = Math.floor(Math.random() * 255);
    var g = Math.floor(Math.random() * 255);
    var b = Math.floor(Math.random() * 255);
    return "rgb(" + r + "," + g + "," + b + ")";
  };
}
