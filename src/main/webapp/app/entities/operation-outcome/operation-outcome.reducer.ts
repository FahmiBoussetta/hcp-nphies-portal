import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOperationOutcome, defaultValue } from 'app/shared/model/operation-outcome.model';

export const ACTION_TYPES = {
  FETCH_OPERATIONOUTCOME_LIST: 'operationOutcome/FETCH_OPERATIONOUTCOME_LIST',
  FETCH_OPERATIONOUTCOME: 'operationOutcome/FETCH_OPERATIONOUTCOME',
  CREATE_OPERATIONOUTCOME: 'operationOutcome/CREATE_OPERATIONOUTCOME',
  UPDATE_OPERATIONOUTCOME: 'operationOutcome/UPDATE_OPERATIONOUTCOME',
  PARTIAL_UPDATE_OPERATIONOUTCOME: 'operationOutcome/PARTIAL_UPDATE_OPERATIONOUTCOME',
  DELETE_OPERATIONOUTCOME: 'operationOutcome/DELETE_OPERATIONOUTCOME',
  RESET: 'operationOutcome/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOperationOutcome>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type OperationOutcomeState = Readonly<typeof initialState>;

// Reducer

export default (state: OperationOutcomeState = initialState, action): OperationOutcomeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_OPERATIONOUTCOME_LIST):
    case REQUEST(ACTION_TYPES.FETCH_OPERATIONOUTCOME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_OPERATIONOUTCOME):
    case REQUEST(ACTION_TYPES.UPDATE_OPERATIONOUTCOME):
    case REQUEST(ACTION_TYPES.DELETE_OPERATIONOUTCOME):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_OPERATIONOUTCOME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_OPERATIONOUTCOME_LIST):
    case FAILURE(ACTION_TYPES.FETCH_OPERATIONOUTCOME):
    case FAILURE(ACTION_TYPES.CREATE_OPERATIONOUTCOME):
    case FAILURE(ACTION_TYPES.UPDATE_OPERATIONOUTCOME):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_OPERATIONOUTCOME):
    case FAILURE(ACTION_TYPES.DELETE_OPERATIONOUTCOME):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPERATIONOUTCOME_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_OPERATIONOUTCOME):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_OPERATIONOUTCOME):
    case SUCCESS(ACTION_TYPES.UPDATE_OPERATIONOUTCOME):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_OPERATIONOUTCOME):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_OPERATIONOUTCOME):
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

const apiUrl = 'api/operation-outcomes';

// Actions

export const getEntities: ICrudGetAllAction<IOperationOutcome> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_OPERATIONOUTCOME_LIST,
  payload: axios.get<IOperationOutcome>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IOperationOutcome> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_OPERATIONOUTCOME,
    payload: axios.get<IOperationOutcome>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOperationOutcome> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_OPERATIONOUTCOME,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOperationOutcome> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_OPERATIONOUTCOME,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOperationOutcome> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_OPERATIONOUTCOME,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOperationOutcome> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_OPERATIONOUTCOME,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
