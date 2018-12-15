import { Component, OnInit, ViewEncapsulation, SecurityContext } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ChartsService } from '../services/charts.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-sentiments',
  templateUrl: './sentiments.component.html',
  styleUrls: ['./sentiments.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class SentimentsComponent implements OnInit {

  isDataAvailable: boolean = false;
  username: any;
  sentiments = []
  percentageNegative;
  percentageNeutral;
  percentagePositive;
  profileLink: string;
  tweetId;
  oembedHtml;

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService, private sanitizer: DomSanitizer) {
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

          // Get oembed tweet
    this.chartService.getLastMentionOEmbedHtml(this.username)
    .subscribe(
      result => {
        console.log("result: " + result['html']);
        let index = result['html'].lastIndexOf("blockquote"); 
        this.oembedHtml = result['html'].substring(0, index + 11);
      }
    );
  }

  generateMentionsChart() {
    this.router.navigate(['charts', this.username, 'mentions-chart']);
  }

}
