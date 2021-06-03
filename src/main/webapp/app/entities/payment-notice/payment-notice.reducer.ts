import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPaymentNotice, defaultValue } from 'app/shared/model/payment-notice.model';

export const ACTION_TYPES = {
  FETCH_PAYMENTNOTICE_LIST: 'paymentNotice/FETCH_PAYMENTNOTICE_LIST',
  FETCH_PAYMENTNOTICE: 'paymentNotice/FETCH_PAYMENTNOTICE',
  CREATE_PAYMENTNOTICE: 'paymentNotice/CREATE_PAYMENTNOTICE',
  UPDATE_PAYMENTNOTICE: 'paymentNotice/UPDATE_PAYMENTNOTICE',
  PARTIAL_UPDATE_PAYMENTNOTICE: 'paymentNotice/PARTIAL_UPDATE_PAYMENTNOTICE',
  DELETE_PAYMENTNOTICE: 'paymentNotice/DELETE_PAYMENTNOTICE',
  RESET: 'paymentNotice/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPaymentNotice>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PaymentNoticeState = Readonly<typeof initialState>;

// Reducer

export default (state: PaymentNoticeState = initialState, action): PaymentNoticeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAYMENTNOTICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYMENTNOTICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYMENTNOTICE):
    case REQUEST(ACTION_TYPES.UPDATE_PAYMENTNOTICE):
    case REQUEST(ACTION_TYPES.DELETE_PAYMENTNOTICE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAYMENTNOTICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAYMENTNOTICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYMENTNOTICE):
    case FAILURE(ACTION_TYPES.CREATE_PAYMENTNOTICE):
    case FAILURE(ACTION_TYPES.UPDATE_PAYMENTNOTICE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAYMENTNOTICE):
    case FAILURE(ACTION_TYPES.DELETE_PAYMENTNOTICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENTNOTICE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYMENTNOTICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYMENTNOTICE):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYMENTNOTICE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAYMENTNOTICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYMENTNOTICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/payment-notices';

// Actions

export const getEntities: ICrudGetAllAction<IPaymentNotice> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAYMENTNOTICE_LIST,
  payload: axios.get<IPaymentNotice>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPaymentNotice> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYMENTNOTICE,
    payload: axios.get<IPaymentNotice>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPaymentNotice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYMENTNOTICE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPaymentNotice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYMENTNOTICE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPaymentNotice> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAYMENTNOTICE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPaymentNotice> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYMENTNOTICE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
