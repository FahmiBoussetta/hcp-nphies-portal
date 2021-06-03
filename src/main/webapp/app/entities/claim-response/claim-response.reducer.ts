import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClaimResponse, defaultValue } from 'app/shared/model/claim-response.model';

export const ACTION_TYPES = {
  FETCH_CLAIMRESPONSE_LIST: 'claimResponse/FETCH_CLAIMRESPONSE_LIST',
  FETCH_CLAIMRESPONSE: 'claimResponse/FETCH_CLAIMRESPONSE',
  CREATE_CLAIMRESPONSE: 'claimResponse/CREATE_CLAIMRESPONSE',
  UPDATE_CLAIMRESPONSE: 'claimResponse/UPDATE_CLAIMRESPONSE',
  PARTIAL_UPDATE_CLAIMRESPONSE: 'claimResponse/PARTIAL_UPDATE_CLAIMRESPONSE',
  DELETE_CLAIMRESPONSE: 'claimResponse/DELETE_CLAIMRESPONSE',
  RESET: 'claimResponse/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClaimResponse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ClaimResponseState = Readonly<typeof initialState>;

// Reducer

export default (state: ClaimResponseState = initialState, action): ClaimResponseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLAIMRESPONSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLAIMRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CLAIMRESPONSE):
    case REQUEST(ACTION_TYPES.UPDATE_CLAIMRESPONSE):
    case REQUEST(ACTION_TYPES.DELETE_CLAIMRESPONSE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CLAIMRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CLAIMRESPONSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLAIMRESPONSE):
    case FAILURE(ACTION_TYPES.CREATE_CLAIMRESPONSE):
    case FAILURE(ACTION_TYPES.UPDATE_CLAIMRESPONSE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CLAIMRESPONSE):
    case FAILURE(ACTION_TYPES.DELETE_CLAIMRESPONSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLAIMRESPONSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLAIMRESPONSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLAIMRESPONSE):
    case SUCCESS(ACTION_TYPES.UPDATE_CLAIMRESPONSE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CLAIMRESPONSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLAIMRESPONSE):
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

const apiUrl = 'api/claim-responses';

// Actions

export const getEntities: ICrudGetAllAction<IClaimResponse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CLAIMRESPONSE_LIST,
  payload: axios.get<IClaimResponse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IClaimResponse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLAIMRESPONSE,
    payload: axios.get<IClaimResponse>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IClaimResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLAIMRESPONSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClaimResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLAIMRESPONSE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IClaimResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CLAIMRESPONSE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClaimResponse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLAIMRESPONSE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
