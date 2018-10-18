import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { ChartsService } from '../services/charts.service';

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent {
  private username: any;

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
    console.log('Initializing charts for', this.username);

    // for last tweet and followers counts
    this.chartService.getUserProfile(this.username)
      .subscribe(result => {
        console.log(result);
      });

    // for tweet and follower count charts
    this.chartService.getLastProfiles(this.username)
      .subscribe(result => {
        console.log(result);
      });

    // for last mentioners sentiments
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
      });
  }
}
