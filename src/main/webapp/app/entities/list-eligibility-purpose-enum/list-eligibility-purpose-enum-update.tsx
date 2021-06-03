import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICoverageEligibilityRequest } from 'app/shared/model/coverage-eligibility-request.model';
import { getEntities as getCoverageEligibilityRequests } from 'app/entities/coverage-eligibility-request/coverage-eligibility-request.reducer';
import { getEntity, updateEntity, createEntity, reset } from './list-eligibility-purpose-enum.reducer';
import { IListEligibilityPurposeEnum } from 'app/shared/model/list-eligibility-purpose-enum.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IListEligibilityPurposeEnumUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ListEligibilityPurposeEnumUpdate = (props: IListEligibilityPurposeEnumUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { listEligibilityPurposeEnumEntity, coverageEligibilityRequests, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/list-eligibility-purpose-enum');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCoverageEligibilityRequests();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...listEligibilityPurposeEnumEntity,
        ...values,
        coverageEligibilityRequest: coverageEligibilityRequests.find(
          it => it.id.toString() === values.coverageEligibilityRequestId.toString()
        ),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="hcpNphiesPortalApp.listEligibilityPurposeEnum.home.createOrEditLabel"
            data-cy="ListEligibilityPurposeEnumCreateUpdateHeading"
          >
            <Translate contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.home.createOrEditLabel">
              Create or edit a ListEligibilityPurposeEnum
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : listEligibilityPurposeEnumEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="list-eligibility-purpose-enum-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="list-eligibility-purpose-enum-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="erpLabel" for="list-eligibility-purpose-enum-erp">
                  <Translate contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.erp">Erp</Translate>
                </Label>
                <AvInput
                  id="list-eligibility-purpose-enum-erp"
                  data-cy="erp"
                  type="select"
                  className="form-control"
                  name="erp"
                  value={(!isNew && listEligibilityPurposeEnumEntity.erp) || 'Benefits'}
                >
                  <option value="Benefits">{translate('hcpNphiesPortalApp.EligibilityPurposeEnum.Benefits')}</option>
                  <option value="Discovery">{translate('hcpNphiesPortalApp.EligibilityPurposeEnum.Discovery')}</option>
                  <option value="Validation">{translate('hcpNphiesPortalApp.EligibilityPurposeEnum.Validation')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="list-eligibility-purpose-enum-coverageEligibilityRequest">
                  <Translate contentKey="hcpNphiesPortalApp.listEligibilityPurposeEnum.coverageEligibilityRequest">
                    Coverage Eligibility Request
                  </Translate>
                </Label>
                <AvInput
                  id="list-eligibility-purpose-enum-coverageEligibilityRequest"
                  data-cy="coverageEligibilityRequest"
                  type="select"
                  className="form-control"
                  name="coverageEligibilityRequestId"
                >
                  <option value="" key="0" />
                  {coverageEligibilityRequests
                    ? coverageEligibilityRequests.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/list-eligibility-purpose-enum" replace color="info">
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
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  coverageEligibilityRequests: storeState.coverageEligibilityRequest.entities,
  listEligibilityPurposeEnumEntity: storeState.listEligibilityPurposeEnum.entity,
  loading: storeState.listEligibilityPurposeEnum.loading,
  updating: storeState.listEligibilityPurposeEnum.updating,
  updateSuccess: storeState.listEligibilityPurposeEnum.updateSuccess,
});

const mapDispatchToProps = {
  getCoverageEligibilityRequests,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ListEligibilityPurposeEnumUpdate);
