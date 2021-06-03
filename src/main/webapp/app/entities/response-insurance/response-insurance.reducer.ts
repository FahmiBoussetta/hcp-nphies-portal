import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IResponseInsurance, defaultValue } from 'app/shared/model/response-insurance.model';

export const ACTION_TYPES = {
  FETCH_RESPONSEINSURANCE_LIST: 'responseInsurance/FETCH_RESPONSEINSURANCE_LIST',
  FETCH_RESPONSEINSURANCE: 'responseInsurance/FETCH_RESPONSEINSURANCE',
  CREATE_RESPONSEINSURANCE: 'responseInsurance/CREATE_RESPONSEINSURANCE',
  UPDATE_RESPONSEINSURANCE: 'responseInsurance/UPDATE_RESPONSEINSURANCE',
  PARTIAL_UPDATE_RESPONSEINSURANCE: 'responseInsurance/PARTIAL_UPDATE_RESPONSEINSURANCE',
  DELETE_RESPONSEINSURANCE: 'responseInsurance/DELETE_RESPONSEINSURANCE',
  RESET: 'responseInsurance/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IResponseInsurance>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ResponseInsuranceState = Readonly<typeof initialState>;

// Reducer

export default (state: ResponseInsuranceState = initialState, action): ResponseInsuranceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RESPONSEINSURANCE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RESPONSEINSURANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RESPONSEINSURANCE):
    case REQUEST(ACTION_TYPES.UPDATE_RESPONSEINSURANCE):
    case REQUEST(ACTION_TYPES.DELETE_RESPONSEINSURANCE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RESPONSEINSURANCE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RESPONSEINSURANCE):
    case FAILURE(ACTION_TYPES.CREATE_RESPONSEINSURANCE):
    case FAILURE(ACTION_TYPES.UPDATE_RESPONSEINSURANCE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCE):
    case FAILURE(ACTION_TYPES.DELETE_RESPONSEINSURANCE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSEINSURANCE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RESPONSEINSURANCE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RESPONSEINSURANCE):
    case SUCCESS(ACTION_TYPES.UPDATE_RESPONSEINSURANCE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RESPONSEINSURANCE):
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

const apiUrl = 'api/response-insurances';

// Actions

export const getEntities: ICrudGetAllAction<IResponseInsurance> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RESPONSEINSURANCE_LIST,
  payload: axios.get<IResponseInsurance>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IResponseInsurance> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RESPONSEINSURANCE,
    payload: axios.get<IResponseInsurance>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IResponseInsurance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RESPONSEINSURANCE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IResponseInsurance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RESPONSEINSURANCE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IResponseInsurance> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RESPONSEINSURANCE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IResponseInsurance> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RESPONSEINSURANCE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
