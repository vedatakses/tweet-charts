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
  
  tiles: Tile[] = [
    {text: 'One', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightgreen'},
    {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
    {text: 'Four', cols: 2, rows: 1, color: '#DDBDF1'},
  ];

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
    console.log('Requesting charts for', this.username);

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

export interface Tile {
  color: string;
  cols: number;
  rows: number;
  text: string;
}