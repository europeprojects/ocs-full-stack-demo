import config from './Config'
import {createApi} from "../components/ApiClient";
import {PagedPollSummaryResponse, PollRequest, PollResponse} from "../types/PollModel";

export default class PollApi {

  static api = createApi(config.POLL_API_URL);

  static createPoll(request: PollRequest) : Promise<PollResponse> {
    return this.api.post('/api/polls/create', request)
              .then(res => res.data)
  }

  static proposePoll(request: PollRequest) : Promise<PollResponse> {
    return this.api.post('/api/polls/propose', request)
        .then(res => res.data)
  }

  static getPolls(): Promise<PagedPollSummaryResponse> {
    return this.api.get('/api/polls/')
        .then(res => res.data)
  }

  static getProposalPolls(): Promise<PagedPollSummaryResponse> {
    return this.api.get('/api/polls/proposal')
        .then(res => res.data)
  }

  static getEndUserPolls(): Promise<PagedPollSummaryResponse> {
    return this.api.get('/api/polls/enduser')
        .then(res => res.data)
  }

  static deletePoll(id:number): Promise<boolean> {
    return this.api.post(`/api/polls/${id}/delete`)
        .then(res => res.data)
  }


  static doPassivePoll(id:number): Promise<boolean> {
    return this.api.post(`/api/polls/${id}/doPassive`)
        .then(res => res.data)
  }


  static votedPoll(pollId:number,choiceId:number): Promise<boolean> {
    return this.api.post(`/api/polls/${pollId}/${choiceId}/voted`)
        .then(res => res.data)
  }

  static doConfirmPoll(id:number): Promise<boolean> {
    return this.api.post(`/api/polls/${id}/confirm`)
        .then(res => res.data)
  }

}
