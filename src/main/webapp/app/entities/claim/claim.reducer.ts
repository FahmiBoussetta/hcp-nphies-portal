import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClaim, defaultValue } from 'app/shared/model/claim.model';

export const ACTION_TYPES = {
  FETCH_CLAIM_LIST: 'claim/FETCH_CLAIM_LIST',
  FETCH_CLAIM: 'claim/FETCH_CLAIM',
  CREATE_CLAIM: 'claim/CREATE_CLAIM',
  UPDATE_CLAIM: 'claim/UPDATE_CLAIM',
  PARTIAL_UPDATE_CLAIM: 'claim/PARTIAL_UPDATE_CLAIM',
  DELETE_CLAIM: 'claim/DELETE_CLAIM',
  RESET: 'claim/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClaim>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ClaimState = Readonly<typeof initialState>;

// Reducer

export default (state: ClaimState = initialState, action): ClaimState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLAIM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLAIM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CLAIM):
    case REQUEST(ACTION_TYPES.UPDATE_CLAIM):
    case REQUEST(ACTION_TYPES.DELETE_CLAIM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CLAIM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CLAIM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLAIM):
    case FAILURE(ACTION_TYPES.CREATE_CLAIM):
    case FAILURE(ACTION_TYPES.UPDATE_CLAIM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CLAIM):
    case FAILURE(ACTION_TYPES.DELETE_CLAIM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLAIM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLAIM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLAIM):
    case SUCCESS(ACTION_TYPES.UPDATE_CLAIM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CLAIM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLAIM):
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

const apiUrl = 'api/claims';

// Actions

export const getEntities: ICrudGetAllAction<IClaim> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CLAIM_LIST,
  payload: axios.get<IClaim>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IClaim> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLAIM,
    payload: axios.get<IClaim>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IClaim> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLAIM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClaim> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLAIM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IClaim> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CLAIM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClaim> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLAIM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
