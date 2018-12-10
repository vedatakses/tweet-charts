import { Component, OnInit, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ChartsService } from '../services/charts.service';

@Component({
  selector: 'app-sentiments',
  templateUrl: './sentiments.component.html',
  styleUrls: ['./sentiments.component.css']
})
export class SentimentsComponent implements OnInit, AfterViewInit {

  username: any;
  sentiments = []
  percentageNegative;
  percentageNeutral;
  percentagePositive;
  profileLink: string;
  tweetId;

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService) { }

  ngAfterViewInit() {
    // @ts-ignore
    twttr.widgets.load();
  }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
    this.profileLink = "https://twitter.com/".concat(this.username);
    let sentiments;
    let sentimentsNegative = 0;
    let sentimentsNeutral = 0;
    let sentimentsPositive = 0;

    // for last mentioners locations
    this.chartService.getLastMetionsSentiments(this.username)
      .subscribe(result => {
        console.log(result);
        sentiments = result['output'];
        for (let i = 0; i < sentiments.length; i++) {
          if (sentiments[i] == "Negative") {
            sentimentsNegative++;
          }
          if (sentiments[i] == "Neutral") {
            sentimentsNeutral++;
          }
          if (sentiments[i] == "Positive") {
            sentimentsPositive++;
          }
        }
        let total = sentimentsNegative + sentimentsNeutral + sentimentsPositive;
        this.percentageNegative = ((100 * sentimentsNegative) / total).toFixed(2);
        this.percentageNeutral = ((100 * sentimentsNeutral) / total).toFixed(2);
        this.percentagePositive = ((100 * sentimentsPositive) / total).toFixed(2);
      });

    this.chartService.getLastMentionOEmbedHtml(this.username)
      .subscribe(
        result => this.tweetId = result,
        error => console.log("Error: ", error),
        () => this.oembedTweet()
      );
  }

  oembedTweet() {
    let id = '' + 1071884444968079360;
    console.log(id);
    console.log(1071884444968079360);
    // @ts-ignore
    twttr.widgets.createTweet(
      id,
      document.getElementById('oembed-tweet'),
      {
        align: 'left',
        width: 400
      })
      .then(function (el) {
        console.log("Tweet displayed.")
      });
  }

  generateMentionsChart() {
    this.router.navigate(['charts', this.username, 'mentions-chart']);
  }

}
