import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { ChartsService } from '../services/charts.service';

@Component({
  selector: 'app-sentiments',
  templateUrl: './sentiments.component.html',
  styleUrls: ['./sentiments.component.css']
})
export class SentimentsComponent implements OnInit {
  username: any;

  constructor(private route: ActivatedRoute,
    private router: Router, private chartService: ChartsService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => this.username = params['username']);
  }

  generateMentionsChart() {
    this.router.navigate(['charts', this.username, 'mentions-chart']);
  }

}
