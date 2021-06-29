import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICoverage } from 'app/shared/model/coverage.model';
import { getEntities as getCoverages } from 'app/entities/coverage/coverage.reducer';
import { getEntity, updateEntity, createEntity, reset } from './class-component.reducer';
import { IClassComponent } from 'app/shared/model/class-component.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClassComponentUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const coverages = useAppSelector(state => state.coverage.entities);
  const classComponentEntity = useAppSelector(state => state.classComponent.entity);
  const loading = useAppSelector(state => state.classComponent.loading);
  const updating = useAppSelector(state => state.classComponent.updating);
  const updateSuccess = useAppSelector(state => state.classComponent.updateSuccess);

  const handleClose = () => {
    props.history.push('/class-component');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCoverages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...classComponentEntity,
      ...values,
      coverage: coverages.find(it => it.id.toString() === values.coverageId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...classComponentEntity,
          type: 'Group',
          coverageId: classComponentEntity?.coverage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.classComponent.home.createOrEditLabel" data-cy="ClassComponentCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.classComponent.home.createOrEditLabel">Create or edit a ClassComponent</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="class-component-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.classComponent.type')}
                id="class-component-type"
                name="type"
                data-cy="type"
                type="select"
              >
                <option value="Group">{translate('hcpNphiesPortalApp.ClassTypeEnum.Group')}</option>
                <option value="Subgroup">{translate('hcpNphiesPortalApp.ClassTypeEnum.Subgroup')}</option>
                <option value="Plan">{translate('hcpNphiesPortalApp.ClassTypeEnum.Plan')}</option>
                <option value="Subplan">{translate('hcpNphiesPortalApp.ClassTypeEnum.Subplan')}</option>
                <option value="Class">{translate('hcpNphiesPortalApp.ClassTypeEnum.Class')}</option>
                <option value="Subclass">{translate('hcpNphiesPortalApp.ClassTypeEnum.Subclass')}</option>
                <option value="Sequence">{translate('hcpNphiesPortalApp.ClassTypeEnum.Sequence')}</option>
                <option value="Rxbin">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxbin')}</option>
                <option value="Rxpcn">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxpcn')}</option>
                <option value="Rxid">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxid')}</option>
                <option value="Rxgroup">{translate('hcpNphiesPortalApp.ClassTypeEnum.Rxgroup')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('hcpNphiesPortalApp.classComponent.value')}
                id="class-component-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.classComponent.name')}
                id="class-component-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                id="class-component-coverage"
                name="coverageId"
                data-cy="coverage"
                label={translate('hcpNphiesPortalApp.classComponent.coverage')}
                type="select"
              >
                <option value="" key="0" />
                {coverages
                  ? coverages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/class-component" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ClassComponentUpdate;
