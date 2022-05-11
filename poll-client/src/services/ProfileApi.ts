import {createApi} from "../components/ApiClient";
import config from "./Config";


export default class ProfileApi {

  static api = createApi(config.PROFILE_URL);

  static getStockCardDumy() {
    return this.api.get('/stock.json').then(res => res.data)
  }


}
