import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHumanName, defaultValue } from 'app/shared/model/human-name.model';

export const ACTION_TYPES = {
  FETCH_HUMANNAME_LIST: 'humanName/FETCH_HUMANNAME_LIST',
  FETCH_HUMANNAME: 'humanName/FETCH_HUMANNAME',
  CREATE_HUMANNAME: 'humanName/CREATE_HUMANNAME',
  UPDATE_HUMANNAME: 'humanName/UPDATE_HUMANNAME',
  PARTIAL_UPDATE_HUMANNAME: 'humanName/PARTIAL_UPDATE_HUMANNAME',
  DELETE_HUMANNAME: 'humanName/DELETE_HUMANNAME',
  RESET: 'humanName/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHumanName>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type HumanNameState = Readonly<typeof initialState>;

// Reducer

export default (state: HumanNameState = initialState, action): HumanNameState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HUMANNAME_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HUMANNAME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HUMANNAME):
    case REQUEST(ACTION_TYPES.UPDATE_HUMANNAME):
    case REQUEST(ACTION_TYPES.DELETE_HUMANNAME):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_HUMANNAME):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_HUMANNAME_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HUMANNAME):
    case FAILURE(ACTION_TYPES.CREATE_HUMANNAME):
    case FAILURE(ACTION_TYPES.UPDATE_HUMANNAME):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_HUMANNAME):
    case FAILURE(ACTION_TYPES.DELETE_HUMANNAME):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HUMANNAME_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_HUMANNAME):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HUMANNAME):
    case SUCCESS(ACTION_TYPES.UPDATE_HUMANNAME):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_HUMANNAME):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HUMANNAME):
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

const apiUrl = 'api/human-names';

// Actions

export const getEntities: ICrudGetAllAction<IHumanName> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HUMANNAME_LIST,
  payload: axios.get<IHumanName>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IHumanName> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HUMANNAME,
    payload: axios.get<IHumanName>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHumanName> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HUMANNAME,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHumanName> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HUMANNAME,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IHumanName> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_HUMANNAME,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHumanName> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HUMANNAME,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
