import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TweetcountComponent } from './tweetcount.component';

describe('TweetcountComponent', () => {
  let component: TweetcountComponent;
  let fixture: ComponentFixture<TweetcountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TweetcountComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TweetcountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
