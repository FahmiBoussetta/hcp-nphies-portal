import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPayload, defaultValue } from 'app/shared/model/payload.model';

export const ACTION_TYPES = {
  FETCH_PAYLOAD_LIST: 'payload/FETCH_PAYLOAD_LIST',
  FETCH_PAYLOAD: 'payload/FETCH_PAYLOAD',
  CREATE_PAYLOAD: 'payload/CREATE_PAYLOAD',
  UPDATE_PAYLOAD: 'payload/UPDATE_PAYLOAD',
  PARTIAL_UPDATE_PAYLOAD: 'payload/PARTIAL_UPDATE_PAYLOAD',
  DELETE_PAYLOAD: 'payload/DELETE_PAYLOAD',
  RESET: 'payload/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPayload>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type PayloadState = Readonly<typeof initialState>;

// Reducer

export default (state: PayloadState = initialState, action): PayloadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PAYLOAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PAYLOAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PAYLOAD):
    case REQUEST(ACTION_TYPES.UPDATE_PAYLOAD):
    case REQUEST(ACTION_TYPES.DELETE_PAYLOAD):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PAYLOAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PAYLOAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PAYLOAD):
    case FAILURE(ACTION_TYPES.CREATE_PAYLOAD):
    case FAILURE(ACTION_TYPES.UPDATE_PAYLOAD):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PAYLOAD):
    case FAILURE(ACTION_TYPES.DELETE_PAYLOAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYLOAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PAYLOAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PAYLOAD):
    case SUCCESS(ACTION_TYPES.UPDATE_PAYLOAD):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PAYLOAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PAYLOAD):
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

const apiUrl = 'api/payloads';

// Actions

export const getEntities: ICrudGetAllAction<IPayload> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PAYLOAD_LIST,
  payload: axios.get<IPayload>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IPayload> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PAYLOAD,
    payload: axios.get<IPayload>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPayload> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PAYLOAD,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPayload> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PAYLOAD,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPayload> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PAYLOAD,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPayload> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PAYLOAD,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
