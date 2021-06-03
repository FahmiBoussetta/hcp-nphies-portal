import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IClassComponent, defaultValue } from 'app/shared/model/class-component.model';

export const ACTION_TYPES = {
  FETCH_CLASSCOMPONENT_LIST: 'classComponent/FETCH_CLASSCOMPONENT_LIST',
  FETCH_CLASSCOMPONENT: 'classComponent/FETCH_CLASSCOMPONENT',
  CREATE_CLASSCOMPONENT: 'classComponent/CREATE_CLASSCOMPONENT',
  UPDATE_CLASSCOMPONENT: 'classComponent/UPDATE_CLASSCOMPONENT',
  PARTIAL_UPDATE_CLASSCOMPONENT: 'classComponent/PARTIAL_UPDATE_CLASSCOMPONENT',
  DELETE_CLASSCOMPONENT: 'classComponent/DELETE_CLASSCOMPONENT',
  RESET: 'classComponent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IClassComponent>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ClassComponentState = Readonly<typeof initialState>;

// Reducer

export default (state: ClassComponentState = initialState, action): ClassComponentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CLASSCOMPONENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CLASSCOMPONENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CLASSCOMPONENT):
    case REQUEST(ACTION_TYPES.UPDATE_CLASSCOMPONENT):
    case REQUEST(ACTION_TYPES.DELETE_CLASSCOMPONENT):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CLASSCOMPONENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CLASSCOMPONENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CLASSCOMPONENT):
    case FAILURE(ACTION_TYPES.CREATE_CLASSCOMPONENT):
    case FAILURE(ACTION_TYPES.UPDATE_CLASSCOMPONENT):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CLASSCOMPONENT):
    case FAILURE(ACTION_TYPES.DELETE_CLASSCOMPONENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSCOMPONENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CLASSCOMPONENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CLASSCOMPONENT):
    case SUCCESS(ACTION_TYPES.UPDATE_CLASSCOMPONENT):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CLASSCOMPONENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CLASSCOMPONENT):
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

const apiUrl = 'api/class-components';

// Actions

export const getEntities: ICrudGetAllAction<IClassComponent> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CLASSCOMPONENT_LIST,
  payload: axios.get<IClassComponent>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IClassComponent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CLASSCOMPONENT,
    payload: axios.get<IClassComponent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IClassComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CLASSCOMPONENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IClassComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CLASSCOMPONENT,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IClassComponent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CLASSCOMPONENT,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IClassComponent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CLASSCOMPONENT,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
